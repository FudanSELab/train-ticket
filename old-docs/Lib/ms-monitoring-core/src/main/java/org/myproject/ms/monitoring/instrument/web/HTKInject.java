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

import java.net.URI;
import java.util.Collection;
import java.util.Map;

import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.springframework.util.StringUtils;


public class HTKInject {

	private final Chainer tracer;
	private final ChainKeys traceKeys;

	public HTKInject(Chainer tracer, ChainKeys traceKeys) {
		this.tracer = tracer;
		this.traceKeys = traceKeys;
	}

	
	public void addRequestTags(String url, String host, String path, String method) {
		this.tracer.addTag(this.traceKeys.getHttp().getUrl(), url);
		this.tracer.addTag(this.traceKeys.getHttp().getHost(), host);
		this.tracer.addTag(this.traceKeys.getHttp().getPath(), path);
		this.tracer.addTag(this.traceKeys.getHttp().getMethod(), method);
	}

	
	public void addRequestTags(Item span, String url, String host, String path, String method) {
		tagSpan(span, this.traceKeys.getHttp().getUrl(), url);
		tagSpan(span, this.traceKeys.getHttp().getHost(), host);
		tagSpan(span, this.traceKeys.getHttp().getPath(), path);
		tagSpan(span, this.traceKeys.getHttp().getMethod(), method);
	}

	
	public void addRequestTags(Item span, URI uri, String method) {
		addRequestTags(span, uri.toString(), uri.getHost(), uri.getPath(), method);
	}

	
	public void addRequestTags(String url, String host, String path, String method,
			Map<String, ? extends Collection<String>> headers) {
		addRequestTags(url, host, path, method);
		addRequestTagsFromHeaders(headers);
	}

	
	public void tagSpan(Item span, String key, String value) {
		if (span != null && span.isExportable()) {
			span.tag(key, value);
		}
	}

	private void addRequestTagsFromHeaders(Map<String, ? extends Collection<String>> headers) {
		for (String name : this.traceKeys.getHttp().getHeaders()) {
			for (Map.Entry<String, ? extends Collection<String>> entry : headers.entrySet()) {
				addTagForEntry(name, entry.getValue());
			}
		}
	}

	private void addTagForEntry(String name, Collection<String> list) {
		String key = this.traceKeys.getHttp().getPrefix() + name.toLowerCase();
		String value = list.size() == 1 ? list.iterator().next()
				: StringUtils.collectionToDelimitedString(list, ",", "'", "'");
		this.tracer.addTag(key, value);
	}

}
