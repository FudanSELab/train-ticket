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



package org.myproject.ms.monitoring.instrument.rest;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;


public class SHCStra extends HystrixConcurrencyStrategy {

	private static final String HYSTRIX_COMPONENT = "hystrix";
	private static final Log log = LogFactory
			.getLog(SHCStra.class);

	private final Chainer tracer;
	private final ChainKeys traceKeys;
	private HystrixConcurrencyStrategy delegate;

	public SHCStra(Chainer tracer, ChainKeys traceKeys) {
		this.tracer = tracer;
		this.traceKeys = traceKeys;
		try {
			this.delegate = HystrixPlugins.getInstance().getConcurrencyStrategy();
			if (this.delegate instanceof SHCStra) {
				// Welcome to singleton hell...
				return;
			}
			HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins
					.getInstance().getCommandExecutionHook();
			HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance()
					.getEventNotifier();
			HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance()
					.getMetricsPublisher();
			HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance()
					.getPropertiesStrategy();
			logCurrentStateOfHysrixPlugins(eventNotifier, metricsPublisher,
					propertiesStrategy);
			HystrixPlugins.reset();
			HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
			HystrixPlugins.getInstance()
					.registerCommandExecutionHook(commandExecutionHook);
			HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
			HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
			HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
		}
		catch (Exception e) {
			log.error("Failed to register Sleuth Hystrix Concurrency Strategy", e);
		}
	}

	private void logCurrentStateOfHysrixPlugins(HystrixEventNotifier eventNotifier,
			HystrixMetricsPublisher metricsPublisher,
			HystrixPropertiesStrategy propertiesStrategy) {
		if (log.isDebugEnabled()) {
			log.debug("Current Hystrix plugins configuration is [" + "concurrencyStrategy ["
					+ this.delegate + "]," + "eventNotifier [" + eventNotifier + "],"
					+ "metricPublisher [" + metricsPublisher + "]," + "propertiesStrategy ["
					+ propertiesStrategy + "]," + "]");
			log.debug("Registering Sleuth Hystrix Concurrency Strategy.");
		}
	}

	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		if (callable instanceof HystrixTraceCallable) {
			return callable;
		}
		Callable<T> wrappedCallable = this.delegate != null
				? this.delegate.wrapCallable(callable) : callable;
		if (wrappedCallable instanceof HystrixTraceCallable) {
			return wrappedCallable;
		}
		return new HystrixTraceCallable<>(this.tracer, this.traceKeys, wrappedCallable);
	}

	// Visible for testing
	static class HystrixTraceCallable<S> implements Callable<S> {

		private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

		private Chainer tracer;
		private ChainKeys traceKeys;
		private Callable<S> callable;
		private Item parent;

		public HystrixTraceCallable(Chainer tracer, ChainKeys traceKeys,
				Callable<S> callable) {
			this.tracer = tracer;
			this.traceKeys = traceKeys;
			this.callable = callable;
			this.parent = tracer.getCurrentSpan();
		}

		@Override
		public S call() throws Exception {
			Item span = this.parent;
			boolean created = false;
			if (span != null) {
				span = this.tracer.continueSpan(span);
				if (log.isDebugEnabled()) {
					log.debug("Continuing span " + span);
				}
			}
			else {
				span = this.tracer.createSpan(HYSTRIX_COMPONENT);
				created = true;
				if (log.isDebugEnabled()) {
					log.debug("Creating new span " + span);
				}
			}
			if (!span.tags().containsKey(Item.SPAN_LOCAL_COMPONENT_TAG_NAME)) {
				this.tracer.addTag(Item.SPAN_LOCAL_COMPONENT_TAG_NAME, HYSTRIX_COMPONENT);
			}
			String asyncKey = this.traceKeys.getAsync().getPrefix()
					+ this.traceKeys.getAsync().getThreadNameKey();
			if (!span.tags().containsKey(asyncKey)) {
				this.tracer.addTag(asyncKey, Thread.currentThread().getName());
			}
			try {
				return this.callable.call();
			}
			finally {
				if (created) {
					if (log.isDebugEnabled()) {
						log.debug("Closing span since it was created" + span);
					}
					this.tracer.close(span);
				}
				else if(this.tracer.isTracing()) {
					if (log.isDebugEnabled()) {
						log.debug("Detaching span since it was continued " + span);
					}
					this.tracer.detach(span);
				}
			}
		}

	}
}
