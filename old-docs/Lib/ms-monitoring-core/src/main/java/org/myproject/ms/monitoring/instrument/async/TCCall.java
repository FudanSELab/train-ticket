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


@Deprecated
public class TCCall<V> extends ChainCallable<V> implements Callable<V> {

	public TCCall(Chainer tracer, ItemNamer spanNamer, Callable<V> delegate) {
		super(tracer, spanNamer, delegate);
	}

	@Override
	protected Item startSpan() {
		return getTracer().continueSpan(getParent());
	}

	@Override
	protected void close(Item span) {
		if (getTracer().isTracing()) {
			getTracer().detach(span);
		}
	}
}
