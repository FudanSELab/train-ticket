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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.BeanFactory;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;


public class TEServ implements ExecutorService {
	ExecutorService delegate;
	Chainer tracer;
	private final String spanName;
	ChainKeys traceKeys;
	ItemNamer spanNamer;
	BeanFactory beanFactory;

	public TEServ(final ExecutorService delegate, final Chainer tracer,
			ChainKeys traceKeys, ItemNamer spanNamer) {
		this(delegate, tracer, traceKeys, spanNamer, null);
	}

	public TEServ(BeanFactory beanFactory, final ExecutorService delegate) {
		this.delegate = delegate;
		this.beanFactory = beanFactory;
		this.spanName = null;
	}

	public TEServ(final ExecutorService delegate, final Chainer tracer,
			ChainKeys traceKeys, ItemNamer spanNamer, String spanName) {
		this.delegate = delegate;
		this.tracer = tracer;
		this.spanName = spanName;
		this.traceKeys = traceKeys;
		this.spanNamer = spanNamer;
	}

	@Override
	public void execute(Runnable command) {
		final Runnable r = new LCTRun(tracer(), traceKeys(),
				spanNamer(), command, this.spanName);
		this.delegate.execute(r);
	}

	@Override
	public void shutdown() {
		this.delegate.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return this.delegate.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return this.delegate.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return this.delegate.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return this.delegate.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		Callable<T> c = new SCTCall<>(tracer(), traceKeys(),
				spanNamer(), this.spanName, task);
		return this.delegate.submit(c);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		Runnable r = new SCTRun(tracer(), traceKeys(),
				spanNamer(), task, this.spanName);
		return this.delegate.submit(r, result);
	}

	@Override
	public Future<?> submit(Runnable task) {
		Runnable r = new LCTRun(tracer(), traceKeys(),
				spanNamer(), task, this.spanName);
		return this.delegate.submit(r);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return this.delegate.invokeAll(wrapCallableCollection(tasks));
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		return this.delegate.invokeAll(wrapCallableCollection(tasks), timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return this.delegate.invokeAny(wrapCallableCollection(tasks));
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return this.delegate.invokeAny(wrapCallableCollection(tasks), timeout, unit);
	}

	private <T> Collection<? extends Callable<T>> wrapCallableCollection(Collection<? extends Callable<T>> tasks) {
		List<Callable<T>> ts = new ArrayList<>();
		for (Callable<T> task : tasks) {
			if (!(task instanceof SCTCall)) {
				ts.add(new SCTCall<>(tracer(), traceKeys(),
						spanNamer(), this.spanName, task));
			}
		}
		return ts;
	}

	Chainer tracer() {
		if (this.tracer == null && this.beanFactory != null) {
			this.tracer = this.beanFactory.getBean(Chainer.class);
		}
		return this.tracer;
	}

	ChainKeys traceKeys() {
		if (this.traceKeys == null && this.beanFactory != null) {
			this.traceKeys = this.beanFactory.getBean(ChainKeys.class);
		}
		return this.traceKeys;
	}

	ItemNamer spanNamer() {
		if (this.spanNamer == null && this.beanFactory != null) {
			this.spanNamer = this.beanFactory.getBean(ItemNamer.class);
		}
		return this.spanNamer;
	}

}
