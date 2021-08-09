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

import java.io.IOException;
import java.net.URI;

import org.myproject.ms.monitoring.instrument.web.HSInject;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.instrument.web.HTKInject;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AsyncClientHttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


public class TACHRFW extends ATHRInter
		implements ClientHttpRequestFactory, AsyncClientHttpRequestFactory {

	final AsyncClientHttpRequestFactory asyncDelegate;
	final ClientHttpRequestFactory syncDelegate;

	
	public TACHRFW(Chainer tracer,
			HSInject spanInjector,
			AsyncClientHttpRequestFactory asyncDelegate,
			HTKInject httpTraceKeysInjector) {
		super(tracer, spanInjector, httpTraceKeysInjector);
		this.asyncDelegate = asyncDelegate;
		this.syncDelegate = asyncDelegate instanceof ClientHttpRequestFactory ?
				(ClientHttpRequestFactory) asyncDelegate : defaultClientHttpRequestFactory();
	}

	
	public TACHRFW(Chainer tracer,
			HSInject spanInjector, HTKInject httpTraceKeysInjector) {
		super(tracer, spanInjector, httpTraceKeysInjector);
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = defaultClientHttpRequestFactory();
		this.asyncDelegate = simpleClientHttpRequestFactory;
		this.syncDelegate = simpleClientHttpRequestFactory;
	}

	public TACHRFW(Chainer tracer,
			HSInject spanInjector,
			AsyncClientHttpRequestFactory asyncDelegate,
			ClientHttpRequestFactory syncDelegate,
			HTKInject httpTraceKeysInjector) {
		super(tracer, spanInjector, httpTraceKeysInjector);
		this.asyncDelegate = asyncDelegate;
		this.syncDelegate = syncDelegate;
	}

	private SimpleClientHttpRequestFactory defaultClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setTaskExecutor(asyncListenableTaskExecutor(this.tracer));
		return simpleClientHttpRequestFactory;
	}

	private AsyncListenableTaskExecutor asyncListenableTaskExecutor(Chainer tracer) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.initialize();
		return new TALTExec(threadPoolTaskScheduler, tracer);
	}

	@Override
	public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod)
			throws IOException {
		AsyncClientHttpRequest request = this.asyncDelegate
				.createAsyncRequest(uri, httpMethod);
		addRequestTags(request);
		publishStartEvent(request);
		return request;
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod)
			throws IOException {
		ClientHttpRequest request = this.syncDelegate.createRequest(uri, httpMethod);
		addRequestTags(request);
		publishStartEvent(request);
		return request;
	}
}
