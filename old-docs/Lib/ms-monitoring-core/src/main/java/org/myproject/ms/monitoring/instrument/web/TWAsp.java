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

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.instrument.async.SCTCall;
import org.myproject.ms.monitoring.util.ExceptionUtils;
import org.springframework.web.context.request.async.WebAsyncTask;


@Aspect
public class TWAsp {

	private static final Log log = org.apache.commons.logging.LogFactory
			.getLog(TWAsp.class);

	private final Chainer tracer;
	private final ItemNamer spanNamer;
	private final ChainKeys traceKeys;

	public TWAsp(Chainer tracer, ItemNamer spanNamer, ChainKeys traceKeys) {
		this.tracer = tracer;
		this.spanNamer = spanNamer;
		this.traceKeys = traceKeys;
	}

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	private void anyRestControllerAnnotated() { }// NOSONAR

	@Pointcut("@within(org.springframework.stereotype.Controller)")
	private void anyControllerAnnotated() { } // NOSONAR

	@Pointcut("execution(public java.util.concurrent.Callable *(..))")
	private void anyPublicMethodReturningCallable() { } // NOSONAR

	@Pointcut("(anyRestControllerAnnotated() || anyControllerAnnotated()) && anyPublicMethodReturningCallable()")
	private void anyControllerOrRestControllerWithPublicAsyncMethod() { } // NOSONAR

	@Pointcut("execution(public org.springframework.web.context.request.async.WebAsyncTask *(..))")
	private void anyPublicMethodReturningWebAsyncTask() { } // NOSONAR

	@Pointcut("execution(public * org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..)) && args(request, response, handler, ex)")
	private void anyHandlerExceptionResolver(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) { } // NOSONAR

	@Pointcut("(anyRestControllerAnnotated() || anyControllerAnnotated()) && anyPublicMethodReturningWebAsyncTask()")
	private void anyControllerOrRestControllerWithPublicWebAsyncTaskMethod() { } // NOSONAR

	@Around("anyControllerOrRestControllerWithPublicAsyncMethod()")
	@SuppressWarnings("unchecked")
	public Object wrapWithCorrelationId(ProceedingJoinPoint pjp) throws Throwable {
		Callable<Object> callable = (Callable<Object>) pjp.proceed();
		if (this.tracer.isTracing()) {
			if (log.isDebugEnabled()) {
				log.debug("Wrapping callable with span [" + this.tracer.getCurrentSpan() + "]");
			}
			return new SCTCall<>(this.tracer, this.traceKeys, this.spanNamer, callable);
		}
		else {
			return callable;
		}
	}

	@Around("anyControllerOrRestControllerWithPublicWebAsyncTaskMethod()")
	public Object wrapWebAsyncTaskWithCorrelationId(ProceedingJoinPoint pjp) throws Throwable {
		final WebAsyncTask<?> webAsyncTask = (WebAsyncTask<?>) pjp.proceed();
		if (this.tracer.isTracing()) {
			try {
				if (log.isDebugEnabled()) {
					log.debug("Wrapping callable with span [" + this.tracer.getCurrentSpan()
							+ "]");
				}
				Field callableField = WebAsyncTask.class.getDeclaredField("callable");
				callableField.setAccessible(true);
				callableField.set(webAsyncTask, new SCTCall<>(this.tracer,
						this.traceKeys, this.spanNamer, webAsyncTask.getCallable()));
			} catch (NoSuchFieldException ex) {
				log.warn("Cannot wrap webAsyncTask's callable with TraceCallable", ex);
			}
		}
		return webAsyncTask;
	}

	@Around("anyHandlerExceptionResolver(request, response, handler, ex)")
	public Object markRequestForSpanClosing(ProceedingJoinPoint pjp,
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Throwable {
		Item currentSpan = this.tracer.getCurrentSpan();
		try {
			if (!currentSpan.tags().containsKey(Item.SPAN_ERROR_TAG_NAME)) {
				this.tracer.addTag(Item.SPAN_ERROR_TAG_NAME, ExceptionUtils.getExceptionMessage(ex));
			}
			return pjp.proceed();
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("Marking span " + currentSpan + " for closure by Trace Filter");
			}
			request.setAttribute(TFilter.TRACE_CLOSE_SPAN_REQUEST_ATTR, true);
		}
	}

}
