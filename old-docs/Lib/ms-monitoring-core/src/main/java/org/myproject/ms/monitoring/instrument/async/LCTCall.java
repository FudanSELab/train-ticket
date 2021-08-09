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



package org.myproject.ms.monitoring.instrument.async;

import java.util.concurrent.Callable;

import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ChainCallable;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.ChainKeys;


public class LCTCall<V> extends ChainCallable<V> {

	protected static final String ASYNC_COMPONENT = "async";

	private final ChainKeys traceKeys;

	public LCTCall(Chainer tracer, ChainKeys traceKeys,
			ItemNamer spanNamer, Callable<V> delegate) {
		super(tracer, spanNamer, delegate);
		this.traceKeys = traceKeys;
	}

	public LCTCall(Chainer tracer, ChainKeys traceKeys,
			ItemNamer spanNamer, String name, Callable<V> delegate) {
		super(tracer, spanNamer, delegate, name);
		this.traceKeys = traceKeys;
	}

	@Override
	public V call() throws Exception {
		Item span = startSpan();
		try {
			return this.getDelegate().call();
		}
		finally {
			close(span);
		}
	}

	@Override
	protected Item startSpan() {
		Item span = getTracer().createSpan(getSpanName(), getParent());
		getTracer().addTag(Item.SPAN_LOCAL_COMPONENT_TAG_NAME, ASYNC_COMPONENT);
		getTracer().addTag(this.traceKeys.getAsync().getPrefix() +
				this.traceKeys.getAsync().getThreadNameKey(), Thread.currentThread().getName());
		return span;
	}
}
