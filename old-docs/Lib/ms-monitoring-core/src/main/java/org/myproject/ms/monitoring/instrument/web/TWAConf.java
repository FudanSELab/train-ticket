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


package org.myproject.ms.monitoring.instrument.web;

import java.util.regex.Pattern;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.myproject.ms.monitoring.ItemNamer;
import org.myproject.ms.monitoring.ItemReporter;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.INCLUDE;
import static javax.servlet.DispatcherType.REQUEST;


@Configuration
@ConditionalOnProperty(value = "spring.sleuth.web.enabled", matchIfMissing = true)
@ConditionalOnWebApplication
@ConditionalOnBean(Chainer.class)
@AutoConfigureAfter(THAConf.class)
public class TWAConf {

	
	@Configuration
	@ConditionalOnClass(WebMvcConfigurerAdapter.class)
	@Import(TWMConf.class)
	protected static class TraceWebMvcAutoConfiguration {
	}

	@Bean
	public TWAsp traceWebAspect(Chainer tracer, ChainKeys traceKeys,
			ItemNamer spanNamer) {
		return new TWAsp(tracer, spanNamer, traceKeys);
	}

	@Bean
	@ConditionalOnClass(name = "org.springframework.data.rest.webmvc.support.DelegatingHandlerMapping")
	public TSDBPProcess traceSpringDataBeanPostProcessor(
			BeanFactory beanFactory) {
		return new TSDBPProcess(beanFactory);
	}

	@Bean
	public FilterRegistrationBean traceWebFilter(TFilter traceFilter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				traceFilter);
		filterRegistrationBean.setDispatcherTypes(ASYNC, ERROR, FORWARD, INCLUDE,
				REQUEST);
		filterRegistrationBean.setOrder(TFilter.ORDER);
		return filterRegistrationBean;
	}

	@Bean
	public TFilter traceFilter(Chainer tracer, ChainKeys traceKeys,
			SkipPatternProvider skipPatternProvider, ItemReporter spanReporter,
			HSExtra spanExtractor,
			HTKInject httpTraceKeysInjector) {
		return new TFilter(tracer, traceKeys, skipPatternProvider.skipPattern(),
				spanReporter, spanExtractor, httpTraceKeysInjector);
	}

	@Configuration
	@ConditionalOnClass(ManagementServerProperties.class)
	@ConditionalOnMissingBean(SkipPatternProvider.class)
	@EnableConfigurationProperties(SWProp.class)
	protected static class SkipPatternProviderConfig {

		@Bean
		@ConditionalOnBean(ManagementServerProperties.class)
		public SkipPatternProvider skipPatternForManagementServerProperties(
				final ManagementServerProperties managementServerProperties,
				final SWProp sleuthWebProperties) {
			return new SkipPatternProvider() {
				@Override
				public Pattern skipPattern() {
					return getPatternForManagementServerProperties(
							managementServerProperties,
							sleuthWebProperties);
				}
			};
		}

		
		static Pattern getPatternForManagementServerProperties(
				ManagementServerProperties managementServerProperties,
				SWProp sleuthWebProperties) {
			String skipPattern = sleuthWebProperties.getSkipPattern();
			if (StringUtils.hasText(skipPattern)
					&& StringUtils.hasText(managementServerProperties.getContextPath())) {
				return Pattern.compile(skipPattern + "|"
						+ managementServerProperties.getContextPath() + ".*");
			}
			else if (StringUtils.hasText(managementServerProperties.getContextPath())) {
				return Pattern
						.compile(managementServerProperties.getContextPath() + ".*");
			}
			return defaultSkipPattern(skipPattern);
		}

		@Bean
		@ConditionalOnMissingBean(ManagementServerProperties.class)
		public SkipPatternProvider defaultSkipPatternBeanIfManagementServerPropsArePresent(SWProp sleuthWebProperties) {
			return defaultSkipPatternProvider(sleuthWebProperties.getSkipPattern());
		}
	}

	@Bean
	@ConditionalOnMissingClass("org.springframework.boot.actuate.autoconfigure.ManagementServerProperties")
	@ConditionalOnMissingBean(SkipPatternProvider.class)
	public SkipPatternProvider defaultSkipPatternBean(SWProp sleuthWebProperties) {
		return defaultSkipPatternProvider(sleuthWebProperties.getSkipPattern());
	}

	private static SkipPatternProvider defaultSkipPatternProvider(
			final String skipPattern) {
		return new SkipPatternProvider() {
			@Override
			public Pattern skipPattern() {
				return defaultSkipPattern(skipPattern);
			}
		};
	}

	private static Pattern defaultSkipPattern(String skipPattern) {
		return StringUtils.hasText(skipPattern) ? Pattern.compile(skipPattern)
				: Pattern.compile(SWProp.DEFAULT_SKIP_PATTERN);
	}

	interface SkipPatternProvider {
		Pattern skipPattern();
	}
}
