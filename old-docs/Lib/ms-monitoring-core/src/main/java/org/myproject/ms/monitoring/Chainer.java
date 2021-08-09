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

import java.util.concurrent.Callable;


public interface Chainer extends ItemAccessor {

	
	Item createSpan(String name);

	
	Item createSpan(String name, Item parent);

	
	Item createSpan(String name, Sampler sampler);

	
	Item continueSpan(Item span);

	
	void addTag(String key, String value);

	
	Item detach(Item span);

	
	Item close(Item span);

	
	<V> Callable<V> wrap(Callable<V> callable);

	
	Runnable wrap(Runnable runnable);
}
