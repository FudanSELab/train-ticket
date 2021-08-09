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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.myproject.ms.monitoring.instrument.web.HSInject;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.instrument.web.HTKInject;
import org.myproject.ms.monitoring.instrument.web.TWAConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.AsyncRestTemplate;


@Configuration
@SWCEnable
@ConditionalOnProperty(value = "spring.sleuth.web.async.client.enabled", matchIfMissing = true)
@ConditionalOnClass(AsyncRestTemplate.class)
@ConditionalOnBean(HTKInject.class)
@AutoConfigureAfter(TWAConf.class)
public class TWACAConf {

	@Autowired Chainer tracer;
	@Autowired private HTKInject httpTraceKeysInjector;
	@Autowired private HSInject spanInjector;
	@Autowired(required = false) private ClientHttpRequestFactory clientHttpRequestFactory;
	@Autowired(required = false) private AsyncClientHttpRequestFactory asyncClientHttpRequestFactory;

	private TACHRFW traceAsyncClientHttpRequestFactory() {
		ClientHttpRequestFactory clientFactory = this.clientHttpRequestFactory;
		AsyncClientHttpRequestFactory asyncClientFactory = this.asyncClientHttpRequestFactory;
		if (clientFactory == null) {
			clientFactory = defaultClientHttpRequestFactory(this.tracer);
		}
		if (asyncClientFactory == null) {
			asyncClientFactory = clientFactory instanceof AsyncClientHttpRequestFactory ?
					(AsyncClientHttpRequestFactory) clientFactory : defaultClientHttpRequestFactory(this.tracer);
		}
		return new TACHRFW(this.tracer, this.spanInjector,
				asyncClientFactory, clientFactory, this.httpTraceKeysInjector);
	}

	private SimpleClientHttpRequestFactory defaultClientHttpRequestFactory(Chainer tracer) {
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setTaskExecutor(asyncListenableTaskExecutor(tracer));
		return simpleClientHttpRequestFactory;
	}

	private AsyncListenableTaskExecutor asyncListenableTaskExecutor(Chainer tracer) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.initialize();
		return new TALTExec(threadPoolTaskScheduler, tracer);
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "spring.sleuth.web.async.client.template.enabled", matchIfMissing = true)
	public AsyncRestTemplate traceAsyncRestTemplate() {
		return new TARTemp(traceAsyncClientHttpRequestFactory(), this.tracer);
	}

}
