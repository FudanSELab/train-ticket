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

import java.lang.invoke.MethodHandles;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.myproject.ms.monitoring.DefaultItemNamer;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;


public class LTExec implements Executor {

	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	private Chainer tracer;
	private final BeanFactory beanFactory;
	private final Executor delegate;
	private ChainKeys traceKeys;
	private ItemNamer spanNamer;

	public LTExec(BeanFactory beanFactory, Executor delegate) {
		this.beanFactory = beanFactory;
		this.delegate = delegate;
	}

	@Override
	public void execute(Runnable command) {
		if (this.tracer == null) {
			try {
				this.tracer = this.beanFactory.getBean(Chainer.class);
			}
			catch (NoSuchBeanDefinitionException e) {
				this.delegate.execute(command);
				return;
			}
		}
		this.delegate.execute(new SCTRun(this.tracer, traceKeys(), spanNamer(), command));
	}

	// due to some race conditions trace keys might not be ready yet
	private ChainKeys traceKeys() {
		if (this.traceKeys == null) {
			try {
				this.traceKeys = this.beanFactory.getBean(ChainKeys.class);
			}
			catch (NoSuchBeanDefinitionException e) {
				log.warn("TraceKeys bean not found - will provide a manually created instance");
				return new ChainKeys();
			}
		}
		return this.traceKeys;
	}

	// due to some race conditions trace keys might not be ready yet
	private ItemNamer spanNamer() {
		if (this.spanNamer == null) {
			try {
				this.spanNamer = this.beanFactory.getBean(ItemNamer.class);
			}
			catch (NoSuchBeanDefinitionException e) {
				log.warn("SpanNamer bean not found - will provide a manually created instance");
				return new DefaultItemNamer();
			}
		}
		return this.spanNamer;
	}

}
