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



package org.myproject.ms.monitoring.instrument.msg;

import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.springframework.integration.channel.ChannelInterceptorAware;
import org.springframework.integration.channel.interceptor.VetoCapableInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;


class ITCInter extends TCInter implements VetoCapableInterceptor {


	public ITCInter(Chainer tracer, ChainKeys traceKeys,
			MSTMExtra spanExtractor,
			MSTMInject spanInjector) {
		super(tracer, traceKeys, spanExtractor, spanInjector);
	}

	@Override
	public boolean shouldIntercept(String beanName, ChannelInterceptorAware channel) {
		for (ChannelInterceptor interceptor : channel.getChannelInterceptors()) {
			if (interceptor instanceof ATCInter) {
				return false;
			}
		}
		return true;
	}

}
