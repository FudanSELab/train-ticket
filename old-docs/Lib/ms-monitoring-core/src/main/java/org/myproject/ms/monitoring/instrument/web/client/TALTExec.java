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

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.myproject.ms.monitoring.Chainer;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;


public class TALTExec implements AsyncListenableTaskExecutor {

	private final AsyncListenableTaskExecutor delegate;
	private final Chainer tracer;

	TALTExec(AsyncListenableTaskExecutor delegate,
			Chainer tracer) {
		this.delegate = delegate;
		this.tracer = tracer;
	}

	@Override
	public ListenableFuture<?> submitListenable(Runnable task) {
		return this.delegate.submitListenable(this.tracer.wrap(task));
	}

	@Override
	public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
		return this.delegate.submitListenable(this.tracer.wrap(task));
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		this.delegate.execute(this.tracer.wrap(task), startTimeout);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return this.delegate.submit(this.tracer.wrap(task));
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return this.delegate.submit(this.tracer.wrap(task));
	}

	@Override
	public void execute(Runnable task) {
		this.delegate.execute(this.tracer.wrap(task));
	}

}