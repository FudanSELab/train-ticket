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



package org.myproject.ms.monitoring;


public class ChainRunnable implements Runnable {

	
	private static final String DEFAULT_SPAN_NAME = "async";

	private final Chainer tracer;
	private final ItemNamer spanNamer;
	private final Runnable delegate;
	private final String name;
	private final Item parent;

	public ChainRunnable(Chainer tracer, ItemNamer spanNamer, Runnable delegate) {
		this(tracer, spanNamer, delegate, null);
	}

	public ChainRunnable(Chainer tracer, ItemNamer spanNamer, Runnable delegate, String name) {
		this.tracer = tracer;
		this.spanNamer = spanNamer;
		this.delegate = delegate;
		this.name = name;
		this.parent = tracer.getCurrentSpan();
	}

	@Override
	public void run()  {
		Item span = startSpan();
		try {
			this.getDelegate().run();
		}
		finally {
			close(span);
		}
	}

	protected Item startSpan() {
		return this.tracer.createSpan(getSpanName(), this.parent);
	}

	protected String getSpanName() {
		if (this.name != null) {
			return this.name;
		}
		return this.spanNamer.name(this.delegate, DEFAULT_SPAN_NAME);
	}

	protected void close(Item span) {
		// race conditions - check #447
		if (!this.tracer.isTracing()) {
			this.tracer.continueSpan(span);
		}
		this.tracer.close(span);
	}

	protected Item continueSpan(Item span) {
		return this.tracer.continueSpan(span);
	}

	protected Item detachSpan(Item span) {
		if (this.tracer.isTracing()) {
			return this.tracer.detach(span);
		}
		return span;
	}

	public Chainer getTracer() {
		return this.tracer;
	}

	public Runnable getDelegate() {
		return this.delegate;
	}

	public String getName() {
		return this.name;
	}

	public Item getParent() {
		return this.parent;
	}
}
