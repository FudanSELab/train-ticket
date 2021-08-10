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

import java.util.Random;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.atcfg.TraceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.GlobalChannelInterceptor;


@Configuration
@ConditionalOnClass(GlobalChannelInterceptor.class)
@ConditionalOnBean(Chainer.class)
@AutoConfigureAfter({ TraceAutoConfiguration.class,
		TSMAConf.class })
@ConditionalOnProperty(value = "spring.sleuth.integration.enabled", matchIfMissing = true)
@EnableConfigurationProperties(ChainKeys.class)
public class TSIAConf {

	@Bean
	@GlobalChannelInterceptor(patterns = "${spring.sleuth.integration.patterns:*}")
	public TCInter traceChannelInterceptor(Chainer tracer,
			ChainKeys traceKeys, Random random, MSTMExtra spanExtractor,
			MSTMInject spanInjector) {
		return new ITCInter(tracer, traceKeys, spanExtractor,
				spanInjector);
	}

}
