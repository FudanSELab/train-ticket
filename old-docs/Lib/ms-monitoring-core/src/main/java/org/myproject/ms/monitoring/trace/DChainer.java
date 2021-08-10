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



package org.myproject.ms.monitoring.trace;

import java.lang.invoke.MethodHandles;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Sampler;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ItemReporter;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.instrument.async.SCTCall;
import org.myproject.ms.monitoring.instrument.async.SCTRun;
import org.myproject.ms.monitoring.lgger.ItemLogger;
import org.myproject.ms.monitoring.util.ExceptionUtils;
import org.myproject.ms.monitoring.util.ItemNameUtil;


public class DChainer implements Chainer {

	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	private static final int MAX_CHARS_IN_SPAN_NAME = 50;

	private final Sampler defaultSampler;

	private final Random random;

	private final ItemNamer spanNamer;

	private final ItemLogger spanLogger;

	private final ItemReporter spanReporter;

	private final ChainKeys traceKeys;

	private final boolean traceId128;

	@Deprecated
	public DChainer(Sampler defaultSampler, Random random, ItemNamer spanNamer,
			ItemLogger spanLogger, ItemReporter spanReporter) {
		this(defaultSampler, random, spanNamer, spanLogger, spanReporter, false);
	}

	@Deprecated
	public DChainer(Sampler defaultSampler, Random random, ItemNamer spanNamer,
				ItemLogger spanLogger, ItemReporter spanReporter, boolean traceId128) {
		this(defaultSampler, random, spanNamer, spanLogger, spanReporter, traceId128, null);
	}

	public DChainer(Sampler defaultSampler, Random random, ItemNamer spanNamer,
				ItemLogger spanLogger, ItemReporter spanReporter, ChainKeys traceKeys) {
		this(defaultSampler, random, spanNamer, spanLogger, spanReporter, false, traceKeys);
	}

	public DChainer(Sampler defaultSampler, Random random, ItemNamer spanNamer,
				ItemLogger spanLogger, ItemReporter spanReporter, boolean traceId128,
			ChainKeys traceKeys) {
		this.defaultSampler = defaultSampler;
		this.random = random;
		this.spanNamer = spanNamer;
		this.spanLogger = spanLogger;
		this.spanReporter = spanReporter;
		this.traceId128 = traceId128;
		this.traceKeys = traceKeys != null ? traceKeys : new ChainKeys();
	}

	@Override
	public Item createSpan(String name, Item parent) {
		if (parent == null) {
			return createSpan(name);
		}
		return continueSpan(createChild(parent, name));
	}

	@Override
	public Item createSpan(String name) {
		return this.createSpan(name, this.defaultSampler);
	}

	@Override
	public Item createSpan(String name, Sampler sampler) {
		String shortenedName = ItemNameUtil.shorten(name);
		Item span;
		if (isTracing()) {
			span = createChild(getCurrentSpan(), shortenedName);
		}
		else {
			long id = createId();
			span = Item.builder().name(shortenedName)
					.traceIdHigh(this.traceId128 ? createId() : 0L)
					.traceId(id)
					.spanId(id).build();
			if (sampler == null) {
				sampler = this.defaultSampler;
			}
			span = sampledSpan(span, sampler);
			this.spanLogger.logStartedSpan(null, span);
		}
		return continueSpan(span);
	}

	@Override
	public Item detach(Item span) {
		if (span == null) {
			return null;
		}
		Item cur = ICHolder.getCurrentSpan();
		if (cur == null) {
			if (log.isTraceEnabled()) {
				log.trace("Span in the context is null so something has already detached the span. Won't do anything about it");
			}
			return null;
		}
		if (!span.equals(cur)) {
			ExceptionUtils.warn("Tried to detach trace span but "
					+ "it is not the current span: " + span
					+ ". You may have forgotten to close or detach " + cur);
		}
		else {
			ICHolder.removeCurrentSpan();
		}
		return span.getSavedSpan();
	}

	@Override
	public Item close(Item span) {
		if (span == null) {
			return null;
		}
		Item cur = ICHolder.getCurrentSpan();
		final Item savedSpan = span.getSavedSpan();
		if (!span.equals(cur)) {
			ExceptionUtils.warn(
					"Tried to close span but it is not the current span: " + span
							+ ".  You may have forgotten to close or detach " + cur);
		}
		else {
			span.stop();
			if (savedSpan != null && span.getParents().contains(savedSpan.getSpanId())) {
				this.spanReporter.report(span);
				this.spanLogger.logStoppedSpan(savedSpan, span);
			}
			else {
				if (!span.isRemote()) {
					this.spanReporter.report(span);
					this.spanLogger.logStoppedSpan(null, span);
				}
			}
			ICHolder.close(new ICHolder.SpanFunction() {
				@Override public void apply(Item span) {
					DChainer.this.spanLogger.logStoppedSpan(savedSpan, span);
				}
			});
		}
		return savedSpan;
	}

	Item createChild(Item parent, String name) {
		String shortenedName = ItemNameUtil.shorten(name);
		long id = createId();
		if (parent == null) {
			Item span = Item.builder().name(shortenedName)
					.traceIdHigh(this.traceId128 ? createId() : 0L)
					.traceId(id)
					.spanId(id).build();
			span = sampledSpan(span, this.defaultSampler);
			this.spanLogger.logStartedSpan(null, span);
			return span;
		}
		else {
			if (!isTracing()) {
				ICHolder.push(parent, true);
			}
			Item span = Item.builder().name(shortenedName)
					.traceIdHigh(parent.getTraceIdHigh())
					.traceId(parent.getTraceId()).parent(parent.getSpanId()).spanId(id)
					.processId(parent.getProcessId()).savedSpan(parent)
					.exportable(parent.isExportable())
					.baggage(parent.getBaggage())
					.build();
			this.spanLogger.logStartedSpan(parent, span);
			return span;
		}
	}

	private Item sampledSpan(Item span, Sampler sampler) {
		if (!sampler.isSampled(span)) {
			// Copy everything, except set exportable to false
			return Item.builder()
					.begin(span.getBegin())
					.traceIdHigh(span.getTraceIdHigh())
					.traceId(span.getTraceId())
					.spanId(span.getSpanId())
					.name(span.getName())
					.exportable(false).build();
		}
		return span;
	}

	private long createId() {
		return this.random.nextLong();
	}

	@Override
	public Item continueSpan(Item span) {
		if (span != null) {
			this.spanLogger.logContinuedSpan(span);
		} else {
			return null;
		}
		Item newSpan = createContinuedSpan(span, ICHolder.getCurrentSpan());
		ICHolder.setCurrentSpan(newSpan);
		return newSpan;
	}

	private Item createContinuedSpan(Item span, Item saved) {
		if (saved == null && span.getSavedSpan() != null) {
			saved = span.getSavedSpan();
		}
		return new Item(span, saved);
	}

	@Override
	public Item getCurrentSpan() {
		return ICHolder.getCurrentSpan();
	}

	@Override
	public boolean isTracing() {
		return ICHolder.isTracing();
	}

	@Override
	public void addTag(String key, String value) {
		Item s = getCurrentSpan();
		if (s != null && s.isExportable()) {
			s.tag(key, value);
		}
	}

	
	@Override
	public <V> Callable<V> wrap(Callable<V> callable) {
		if (isTracing()) {
			return new SCTCall<>(this, this.traceKeys, this.spanNamer, callable);
		}
		return callable;
	}

	
	@Override
	public Runnable wrap(Runnable runnable) {
		if (isTracing()) {
			return new SCTRun(this, this.traceKeys, this.spanNamer, runnable);
		}
		return runnable;
	}
}
