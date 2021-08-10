/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.myproject.ms.monitoring.instrument.web;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemReporter;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.spl.AlwaysSampler;
import org.myproject.ms.monitoring.spl.NeverSampler;
import org.myproject.ms.monitoring.util.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;


@Order(TFilter.ORDER)
public class TFilter extends GenericFilterBean {

	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	private static final String HTTP_COMPONENT = "http";

	
	public static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 5;

	protected static final String TRACE_REQUEST_ATTR = TFilter.class.getName()
			+ ".TRACE";

	protected static final String TRACE_ERROR_HANDLED_REQUEST_ATTR = TFilter.class.getName()
			+ ".ERROR_HANDLED";

	protected static final String TRACE_CLOSE_SPAN_REQUEST_ATTR = TFilter.class.getName()
			+ ".CLOSE_SPAN";

	
	@Deprecated
	public static final String DEFAULT_SKIP_PATTERN = SWProp.DEFAULT_SKIP_PATTERN;

	private final Chainer tracer;
	private final ChainKeys traceKeys;
	private final Pattern skipPattern;
	private final ItemReporter spanReporter;
	private final HSExtra spanExtractor;
	private final HTKInject httpTraceKeysInjector;

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	public TFilter(Chainer tracer, ChainKeys traceKeys, ItemReporter spanReporter,
			HSExtra spanExtractor,
			HTKInject httpTraceKeysInjector) {
		this(tracer, traceKeys, Pattern.compile(SWProp.DEFAULT_SKIP_PATTERN), spanReporter,
				spanExtractor, httpTraceKeysInjector);
	}

	public TFilter(Chainer tracer, ChainKeys traceKeys, Pattern skipPattern,
			ItemReporter spanReporter, HSExtra spanExtractor,
			HTKInject httpTraceKeysInjector) {
		this.tracer = tracer;
		this.traceKeys = traceKeys;
		this.skipPattern = skipPattern;
		this.spanReporter = spanReporter;
		this.spanExtractor = spanExtractor;
		this.httpTraceKeysInjector = httpTraceKeysInjector;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("Filter just supports HTTP requests");
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String uri = this.urlPathHelper.getPathWithinApplication(request);
		boolean skip = this.skipPattern.matcher(uri).matches()
				|| Item.SPAN_NOT_SAMPLED.equals(ServletUtils.getHeader(request, response, Item.SAMPLED_NAME));
		Item spanFromRequest = getSpanFromAttribute(request);
		if (spanFromRequest != null) {
			continueSpan(request, spanFromRequest);
		}
		if (log.isDebugEnabled()) {
			log.debug("Received a request to uri [" + uri + "] that should not be sampled [" + skip + "]");
		}
		// in case of a response with exception status a exception controller will close the span
		if (!httpStatusSuccessful(response) && isSpanContinued(request)) {
			Item parentSpan = parentSpan(spanFromRequest);
			processErrorRequest(filterChain, request, new THSResp(response, parentSpan), spanFromRequest);
			return;
		}
		String name = HTTP_COMPONENT + ":" + uri;
		Throwable exception = null;
		try {
			spanFromRequest = createSpan(request, skip, spanFromRequest, name);
			filterChain.doFilter(request, new THSResp(response, spanFromRequest));
		} catch (Throwable e) {
			exception = e;
			this.tracer.addTag(Item.SPAN_ERROR_TAG_NAME, ExceptionUtils.getExceptionMessage(e));
			throw e;
		} finally {
			if (isAsyncStarted(request) || request.isAsyncStarted()) {
				if (log.isDebugEnabled()) {
					log.debug("The span " + spanFromRequest + " will get detached by a HandleInterceptor");
				}
				// TODO: how to deal with response annotations and async?
				return;
			}
			spanFromRequest = createSpanIfRequestNotHandled(request, spanFromRequest, name, skip);
			detachOrCloseSpans(request, response, spanFromRequest, exception);
		}
	}

	private Item parentSpan(Item span) {
		if (span == null) {
			return null;
		}
		if (span.hasSavedSpan()) {
			return span.getSavedSpan();
		}
		return span;
	}

	private void processErrorRequest(FilterChain filterChain, HttpServletRequest request,
			HttpServletResponse response, Item spanFromRequest)
			throws IOException, ServletException {
		if (log.isDebugEnabled()) {
			log.debug("The span " + spanFromRequest + " was already detached once and we're processing an error");
		}
		try {
			filterChain.doFilter(request, response);
		} finally {
			request.setAttribute(TRACE_ERROR_HANDLED_REQUEST_ATTR, true);
			addResponseTags(response, null);
			if (request.getAttribute(TRAttr.ERROR_HANDLED_SPAN_REQUEST_ATTR) == null) {
				this.tracer.close(spanFromRequest);
			}
		}
	}

	private void continueSpan(HttpServletRequest request, Item spanFromRequest) {
		this.tracer.continueSpan(spanFromRequest);
		request.setAttribute(TRAttr.SPAN_CONTINUED_REQUEST_ATTR, "true");
		if (log.isDebugEnabled()) {
			log.debug("There has already been a span in the request " + spanFromRequest);
		}
	}

	// This method is a fallback in case if handler interceptors didn't catch the request.
	// In that case we are creating an artificial span so that it can be visible in Zipkin.
	private Item createSpanIfRequestNotHandled(HttpServletRequest request,
			Item spanFromRequest, String name, boolean skip) {
		if (!requestHasAlreadyBeenHandled(request)) {
			spanFromRequest = this.tracer.createSpan(name);
			request.setAttribute(TRACE_REQUEST_ATTR, spanFromRequest);
			if (log.isDebugEnabled() && !skip) {
				log.debug("The request with uri [" + request.getRequestURI() + "] hasn't been handled by any of Sleuth's components. "
						+ "That means that most likely you're using custom HandlerMappings and didn't add Sleuth's TraceHandlerInterceptor. "
						+ "Sleuth will create a span to ensure that the graph of calls remains valid in Zipkin");
			}
		}
		return spanFromRequest;
	}

	private boolean requestHasAlreadyBeenHandled(HttpServletRequest request) {
		return request.getAttribute(TRAttr.HANDLED_SPAN_REQUEST_ATTR) != null;
	}

	private void detachOrCloseSpans(HttpServletRequest request,
			HttpServletResponse response, Item spanFromRequest, Throwable exception) {
		Item span = spanFromRequest;
		if (span != null) {
			addResponseTags(response, exception);
			if (span.hasSavedSpan() && requestHasAlreadyBeenHandled(request)) {
				recordParentSpan(span.getSavedSpan());
			} else if (!requestHasAlreadyBeenHandled(request)) {
				span = this.tracer.close(span);
			}
			recordParentSpan(span);
			// in case of a response with exception status will close the span when exception dispatch is handled
			// checking if tracing is in progress due to async / different order of view controller processing
			if (httpStatusSuccessful(response) && this.tracer.isTracing()) {
				if (log.isDebugEnabled()) {
					log.debug("Closing the span " + span + " since the response was successful");
				}
				this.tracer.close(span);
			} else if (errorAlreadyHandled(request) && this.tracer.isTracing()) {
				if (log.isDebugEnabled()) {
					log.debug(
							"Won't detach the span " + span + " since error has already been handled");
				}
			}  else if (shouldCloseSpan(request) && this.tracer.isTracing() && stillTracingCurrentSapn(span)) {
				if (log.isDebugEnabled()) {
					log.debug(
							"Will close span " + span + " since some component marked it for closure");
				}
				this.tracer.close(span);
			} else if (this.tracer.isTracing()) {
				if (log.isDebugEnabled()) {
					log.debug("Detaching the span " + span + " since the response was unsuccessful");
				}
				this.tracer.detach(span);
			}
		}
	}

	private boolean stillTracingCurrentSapn(Item span) {
		return this.tracer.getCurrentSpan().equals(span);
	}

	private void recordParentSpan(Item parent) {
		if (parent == null) {
			return;
		}
		if (parent.isRemote()) {
			if (log.isDebugEnabled()) {
				log.debug("Trying to send the parent span " + parent + " to Zipkin");
			}
			parent.stop();
			// should be already done by HttpServletResponse wrappers
			SsLogSetter.annotateWithServerSendIfLogIsNotAlreadyPresent(parent);
			this.spanReporter.report(parent);
		} else {
			// should be already done by HttpServletResponse wrappers
			SsLogSetter.annotateWithServerSendIfLogIsNotAlreadyPresent(parent);
		}
	}

	private boolean httpStatusSuccessful(HttpServletResponse response) {
		if (response.getStatus() == 0) {
			return false;
		}
		HttpStatus.Series httpStatusSeries = HttpStatus.Series.valueOf(response.getStatus());
		return httpStatusSeries == HttpStatus.Series.SUCCESSFUL || httpStatusSeries == HttpStatus.Series.REDIRECTION;
	}

	private Item getSpanFromAttribute(HttpServletRequest request) {
		return (Item) request.getAttribute(TRACE_REQUEST_ATTR);
	}

	private boolean errorAlreadyHandled(HttpServletRequest request) {
		return Boolean.valueOf(
				String.valueOf(request.getAttribute(TRACE_ERROR_HANDLED_REQUEST_ATTR)));
	}

	private boolean shouldCloseSpan(HttpServletRequest request) {
		return Boolean.valueOf(
				String.valueOf(request.getAttribute(TRACE_CLOSE_SPAN_REQUEST_ATTR)));
	}

	private boolean isSpanContinued(HttpServletRequest request) {
		return getSpanFromAttribute(request) != null;
	}

	
	private void addRequestTagsForParentSpan(HttpServletRequest request, Item spanFromRequest) {
		if (spanFromRequest.getName().contains("parent")) {
			addRequestTags(spanFromRequest, request);
		}
	}

	
	private Item createSpan(HttpServletRequest request,
			boolean skip, Item spanFromRequest, String name) {
		if (spanFromRequest != null) {
			if (log.isDebugEnabled()) {
				log.debug("Span has already been created - continuing with the previous one");
			}
			return spanFromRequest;
		}
		Item parent = this.spanExtractor.joinTrace(new HSRTMap(request));
		if (parent != null) {
			if (log.isDebugEnabled()) {
				log.debug("Found a parent span " + parent + " in the request");
			}
			addRequestTagsForParentSpan(request, parent);
			spanFromRequest = parent;
			this.tracer.continueSpan(spanFromRequest);
			if (parent.isRemote()) {
				parent.logEvent(Item.SERVER_RECV);
			}
			request.setAttribute(TRACE_REQUEST_ATTR, spanFromRequest);
			if (log.isDebugEnabled()) {
				log.debug("Parent span is " + parent + "");
			}
		} else {
			if (skip) {
				spanFromRequest = this.tracer.createSpan(name, NeverSampler.INSTANCE);
			}
			else {
				String header = request.getHeader(Item.SPAN_FLAGS);
				if (Item.SPAN_SAMPLED.equals(header)) {
					spanFromRequest = this.tracer.createSpan(name, new AlwaysSampler());
				} else {
					spanFromRequest = this.tracer.createSpan(name);
				}
			}
			spanFromRequest.logEvent(Item.SERVER_RECV);
			request.setAttribute(TRACE_REQUEST_ATTR, spanFromRequest);
			if (log.isDebugEnabled()) {
				log.debug("No parent span present - creating a new span");
			}
		}
		return spanFromRequest;
	}

	
	protected void addRequestTags(Item span, HttpServletRequest request) {
		String uri = this.urlPathHelper.getPathWithinApplication(request);
		this.httpTraceKeysInjector.addRequestTags(span, getFullUrl(request),
				request.getServerName(), uri, request.getMethod());
		for (String name : this.traceKeys.getHttp().getHeaders()) {
			Enumeration<String> values = request.getHeaders(name);
			if (values.hasMoreElements()) {
				String key = this.traceKeys.getHttp().getPrefix() + name.toLowerCase();
				ArrayList<String> list = Collections.list(values);
				String value = list.size() == 1 ? list.get(0)
						: StringUtils.collectionToDelimitedString(list, ",", "'", "'");
				this.httpTraceKeysInjector.tagSpan(span, key, value);
			}
		}
	}

	
	protected void addResponseTags(HttpServletResponse response, Throwable e) {
		int httpStatus = response.getStatus();
		if (httpStatus == HttpServletResponse.SC_OK && e != null) {
			// Filter chain threw exception but the response status may not have been set
			// yet, so we have to guess.
			this.tracer.addTag(this.traceKeys.getHttp().getStatusCode(),
					String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
		}
		// only tag valid http statuses
		else if (httpStatus >= 100 && (httpStatus < 200) || (httpStatus > 399)) {
			this.tracer.addTag(this.traceKeys.getHttp().getStatusCode(),
					String.valueOf(response.getStatus()));
		}
	}

	protected boolean isAsyncStarted(HttpServletRequest request) {
		return WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted();
	}

	private String getFullUrl(HttpServletRequest request) {
		StringBuffer requestURI = request.getRequestURL();
		String queryString = request.getQueryString();
		if (queryString == null) {
			return requestURI.toString();
		} else {
			return requestURI.append('?').append(queryString).toString();
		}
	}
}

