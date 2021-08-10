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

import org.myproject.ms.monitoring.instrument.web.HSInject;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.instrument.web.HTKInject;
import org.myproject.ms.monitoring.util.ExceptionUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;


public class TRTInter extends ATHRInter
		implements ClientHttpRequestInterceptor {

	public TRTInter(Chainer tracer, HSInject spanInjector,
			HTKInject httpTraceKeysInjector) {
		super(tracer, spanInjector, httpTraceKeysInjector);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		publishStartEvent(request);
		return response(request, body, execution);
	}

	private ClientHttpResponse response(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		try {
			return new THResp(this, execution.execute(request, body));
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Exception occurred while trying to execute the request. Will close the span [" + currentSpan() + "]", e);
			}
			this.tracer.addTag(Item.SPAN_ERROR_TAG_NAME, ExceptionUtils.getExceptionMessage(e));
			finish();
			throw e;
		}
	}

}
