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



package org.myproject.ms.monitoring.atcfg;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.myproject.ms.monitoring.DefaultItemNamer;
import org.myproject.ms.monitoring.NOItemAdjuster;
import org.myproject.ms.monitoring.NOItemReporter;
import org.myproject.ms.monitoring.Sampler;
import org.myproject.ms.monitoring.ItemAdjuster;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ItemReporter;
//import org.myproject.ms.monitoring.StateSpanAdjuster;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.lgger.ItemLogger;
import org.myproject.ms.monitoring.spl.NeverSampler;
import org.myproject.ms.monitoring.trace.DChainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(value="spring.sleuth.enabled", matchIfMissing=true)
@EnableConfigurationProperties({ChainKeys.class, SleuthProperties.class})
public class TraceAutoConfiguration {
	@Autowired
	SleuthProperties properties;

	@Bean
	@ConditionalOnMissingBean
	public Random randomForSpanIds() {
		return new Random();
	}

	@Bean
	@ConditionalOnMissingBean
	public Sampler defaultTraceSampler() {
		return NeverSampler.INSTANCE;
	}

	@Bean
	@ConditionalOnMissingBean(Chainer.class)
	public DChainer sleuthTracer(Sampler sampler, Random random,
			ItemNamer spanNamer, ItemLogger spanLogger,
			ItemReporter spanReporter, ChainKeys traceKeys) {
		return new DChainer(sampler, random, spanNamer, spanLogger,
				spanReporter, this.properties.isTraceId128(), traceKeys);
	}

	@Bean
	@ConditionalOnMissingBean
	public ItemNamer spanNamer() {
		return new DefaultItemNamer();
	}

	@Bean
	@ConditionalOnMissingBean
	public ItemReporter defaultSpanReporter() {
		return new NOItemReporter();
	}

	@Bean
	@ConditionalOnMissingBean
	public ItemAdjuster defaultSpanAdjuster() {
		return new NOItemAdjuster();
//		return new StateSpanAdjuster();
	}

}
