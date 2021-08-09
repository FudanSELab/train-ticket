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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.instrument.web.HSInject;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.instrument.web.HTKInject;
import org.myproject.ms.monitoring.util.ItemNameUtil;
import org.springframework.http.HttpRequest;

abstract class ATHRInter {

	protected static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	protected final Chainer tracer;
	protected final HSInject spanInjector;
	protected final HTKInject keysInjector;

	protected ATHRInter(Chainer tracer,
			HSInject spanInjector, HTKInject keysInjector) {
		this.tracer = tracer;
		this.spanInjector = spanInjector;
		this.keysInjector = keysInjector;
	}

	
	protected void publishStartEvent(HttpRequest request) {
		URI uri = request.getURI();
		String spanName = getName(uri);
		Item newSpan = this.tracer.createSpan(spanName);
		this.spanInjector.inject(newSpan, new HRTMap(request));
		addRequestTags(request);
		newSpan.logEvent(Item.CLIENT_SEND);
		if (log.isDebugEnabled()) {
			log.debug("Starting new client span [" + newSpan + "]");
		}
	}

	private String getName(URI uri) {
		return ItemNameUtil.shorten(uriScheme(uri) + ":" + uri.getPath());
	}

	private String uriScheme(URI uri) {
		return uri.getScheme() == null ? "http" : uri.getScheme();
	}

	
	protected void addRequestTags(HttpRequest request) {
		this.keysInjector.addRequestTags(request.getURI().toString(),
				request.getURI().getHost(),
				request.getURI().getPath(),
				request.getMethod().name(),
				request.getHeaders());
	}

	
	public void finish() {
		if (!isTracing()) {
			return;
		}
		currentSpan().logEvent(Item.CLIENT_RECV);
		this.tracer.close(this.currentSpan());
	}

	protected Item currentSpan() {
		return this.tracer.getCurrentSpan();
	}

	protected boolean isTracing() {
		return this.tracer.isTracing();
	}

}
