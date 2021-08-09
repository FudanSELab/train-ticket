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



package org.myproject.ms.monitoring.instrument.web.client;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.util.ExceptionUtils;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.AsyncRequestCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class TARTemp extends AsyncRestTemplate {

	private final Chainer tracer;

	public TARTemp(Chainer tracer) {
		super();
		this.tracer = tracer;
	}

	public TARTemp(AsyncListenableTaskExecutor taskExecutor, Chainer tracer) {
		super(taskExecutor);
		this.tracer = tracer;
	}

	public TARTemp(AsyncClientHttpRequestFactory asyncRequestFactory,
			Chainer tracer) {
		super(asyncRequestFactory);
		this.tracer = tracer;
	}

	public TARTemp(AsyncClientHttpRequestFactory asyncRequestFactory,
			ClientHttpRequestFactory syncRequestFactory, Chainer tracer) {
		super(asyncRequestFactory, syncRequestFactory);
		this.tracer = tracer;
	}

	public TARTemp(AsyncClientHttpRequestFactory requestFactory,
			RestTemplate restTemplate, Chainer tracer) {
		super(requestFactory, restTemplate);
		this.tracer = tracer;
	}

	@Override
	protected <T> ListenableFuture<T> doExecute(URI url, HttpMethod method,
			AsyncRequestCallback requestCallback, ResponseExtractor<T> responseExtractor)
			throws RestClientException {
		final ListenableFuture<T> future = super.doExecute(url, method, requestCallback, responseExtractor);
		final Item span = this.tracer.getCurrentSpan();
		future.addCallback(new TraceListenableFutureCallback<>(this.tracer, span));
		// potential race can happen here
		if (span != null && span.equals(this.tracer.getCurrentSpan())) {
			this.tracer.detach(span);
		}
		return new ListenableFuture<T>() {

			@Override public boolean cancel(boolean mayInterruptIfRunning) {
				return future.cancel(mayInterruptIfRunning);
			}

			@Override public boolean isCancelled() {
				return future.isCancelled();
			}

			@Override public boolean isDone() {
				return future.isDone();
			}

			@Override public T get() throws InterruptedException, ExecutionException {
				return future.get();
			}

			@Override public T get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				return future.get(timeout, unit);
			}

			@Override
			public void addCallback(ListenableFutureCallback<? super T> callback) {
				future.addCallback(new TraceListenableFutureCallbackWrapper<>(TARTemp.this.tracer, span, callback));
			}

			@Override public void addCallback(SuccessCallback<? super T> successCallback,
					FailureCallback failureCallback) {
				future.addCallback(
						new TraceSuccessCallback<>(TARTemp.this.tracer, span, successCallback),
						new TraceFailureCallback(TARTemp.this.tracer, span, failureCallback));
			}
		};
	}

	private static class TraceSuccessCallback<T> implements SuccessCallback<T> {

		private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

		private final Chainer tracer;
		private final Item parent;
		private final SuccessCallback<T> delegate;

		private TraceSuccessCallback(Chainer tracer, Item parent,
				SuccessCallback<T> delegate) {
			this.tracer = tracer;
			this.parent = parent;
			this.delegate = delegate;
		}

		@Override public void onSuccess(T result) {
			continueSpan();
			if (log.isDebugEnabled()) {
				log.debug("Calling on success of the delegate");
			}
			this.delegate.onSuccess(result);
			finish();
		}

		private void continueSpan() {
			this.tracer.continueSpan(this.parent);
		}

		private void finish() {
			this.tracer.detach(currentSpan());
		}

		private Item currentSpan() {
			return this.tracer.getCurrentSpan();
		}
	}

	private static class TraceFailureCallback implements FailureCallback {

		private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

		private final Chainer tracer;
		private final Item parent;
		private final FailureCallback delegate;

		private TraceFailureCallback(Chainer tracer, Item parent,
				FailureCallback delegate) {
			this.tracer = tracer;
			this.parent = parent;
			this.delegate = delegate;
		}

		@Override public void onFailure(Throwable ex) {
			continueSpan();
			if (log.isDebugEnabled()) {
				log.debug("Calling on failure of the delegate");
			}
			this.delegate.onFailure(ex);
			finish();
		}

		private void continueSpan() {
			this.tracer.continueSpan(this.parent);
		}

		private void finish() {
			this.tracer.detach(currentSpan());
		}

		private Item currentSpan() {
			return this.tracer.getCurrentSpan();
		}
	}

	private static class TraceListenableFutureCallbackWrapper<T> implements ListenableFutureCallback<T> {

		private final Chainer tracer;
		private final Item parent;
		private final ListenableFutureCallback<T> delegate;

		private TraceListenableFutureCallbackWrapper(Chainer tracer, Item parent,
				ListenableFutureCallback<T> delegate) {
			this.tracer = tracer;
			this.parent = parent;
			this.delegate = delegate;
		}

		@Override public void onFailure(Throwable ex) {
			new TraceFailureCallback(this.tracer, this.parent, this.delegate).onFailure(ex);
		}

		@Override public void onSuccess(T result) {
			new TraceSuccessCallback<>(this.tracer, this.parent, this.delegate).onSuccess(result);
		}
	}

	private static class TraceListenableFutureCallback<T> implements ListenableFutureCallback<T> {

		private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

		private final Chainer tracer;
		private final Item parent;

		private TraceListenableFutureCallback(Chainer tracer, Item parent) {
			this.tracer = tracer;
			this.parent = parent;
		}

		@Override
		public void onFailure(Throwable ex) {
			continueSpan();
			if (log.isDebugEnabled()) {
				log.debug("The callback failed - will close the span");
			}
			this.tracer.addTag(Item.SPAN_ERROR_TAG_NAME, ExceptionUtils.getExceptionMessage(ex));
			finish();
		}

		@Override
		public void onSuccess(T result) {
			continueSpan();
			if (log.isDebugEnabled()) {
				log.debug("The callback succeeded - will close the span");
			}
			finish();
		}

		private void continueSpan() {
			this.tracer.continueSpan(this.parent);
		}

		private void finish() {
			if (!isTracing()) {
				return;
			}
			currentSpan().logEvent(Item.CLIENT_RECV);
			this.tracer.close(currentSpan());
		}

		private Item currentSpan() {
			return this.tracer.getCurrentSpan();
		}

		private boolean isTracing() {
			return this.tracer.isTracing();
		}
	}





}
