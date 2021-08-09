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

import java.util.List;
import java.util.Map;

import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemTextMap;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.util.TextMapUtil;
import org.springframework.util.StringUtils;


public class HBMInject implements MSTMInject {

	private final ChainKeys traceKeys;

	public HBMInject(ChainKeys traceKeys) {
		this.traceKeys = traceKeys;
	}

	@Override
	public void inject(Item span, ItemTextMap carrier) {
		Map<String, String> map = TextMapUtil.asMap(carrier);
		if (span == null) {
			if (!isSampled(map, TMHead.SAMPLED_NAME)) {
				carrier.put(TMHead.SAMPLED_NAME, Item.SPAN_NOT_SAMPLED);
				return;
			}
			return;
		}
		addHeaders(span, carrier);
	}

	private boolean isSampled(Map<String, String> initialMessage, String sampledHeaderName) {
		return Item.SPAN_SAMPLED.equals(initialMessage.get(sampledHeaderName));
	}

	private void addHeaders(Item span, ItemTextMap textMap) {
		addHeader(textMap, TMHead.TRACE_ID_NAME, span.traceIdString());
		addHeader(textMap, TMHead.SPAN_ID_NAME, Item.idToHex(span.getSpanId()));
		if (span.isExportable()) {
			addAnnotations(this.traceKeys, textMap, span);
			Long parentId = getFirst(span.getParents());
			if (parentId != null) {
				addHeader(textMap, TMHead.PARENT_ID_NAME, Item.idToHex(parentId));
			}
			addHeader(textMap, TMHead.SPAN_NAME_NAME, span.getName());
			addHeader(textMap, TMHead.PROCESS_ID_NAME, span.getProcessId());
			addHeader(textMap, TMHead.SAMPLED_NAME, Item.SPAN_SAMPLED);
		}
		else {
			addHeader(textMap, TMHead.SAMPLED_NAME, Item.SPAN_NOT_SAMPLED);
		}
		for (Map.Entry<String, String> entry : span.baggageItems()) {
			textMap.put(prefixedKey(entry.getKey()), entry.getValue());
		}
	}

	private void addAnnotations(ChainKeys traceKeys, ItemTextMap spanTextMap, Item span) {
		Map<String, String> map = TextMapUtil.asMap(spanTextMap);
		for (String name : traceKeys.getMessage().getHeaders()) {
			if (map.containsKey(name)) {
				String key = traceKeys.getMessage().getPrefix() + name.toLowerCase();
				Object value = map.get(name);
				if (value == null) {
					value = "null";
				}
				// TODO: better way to serialize?
				tagIfEntryMissing(span, key, value.toString());
			}
		}
		addPayloadAnnotations(traceKeys, map, span);
	}

	private void addPayloadAnnotations(ChainKeys traceKeys, Map<String, String> map, Item span) {
		if (map.containsKey(traceKeys.getMessage().getPayload().getType())) {
			tagIfEntryMissing(span, traceKeys.getMessage().getPayload().getType(),
					map.get(traceKeys.getMessage().getPayload().getType()));
			tagIfEntryMissing(span, traceKeys.getMessage().getPayload().getSize(),
					map.get(traceKeys.getMessage().getPayload().getSize()));
		}
	}

	private void tagIfEntryMissing(Item span, String key, String value) {
		if (!span.tags().containsKey(key)) {
			span.tag(key, value);
		}
	}

	private void addHeader(ItemTextMap textMap, String name, String value) {
		if (StringUtils.hasText(value)) {
			textMap.put(name, value);
		}
	}

	private Long getFirst(List<Long> parents) {
		return parents.isEmpty() ? null : parents.get(0);
	}

	private String prefixedKey(String key) {
		if (key.startsWith(Item.SPAN_BAGGAGE_HEADER_PREFIX + TMHead.HEADER_DELIMITER )) {
			return key;
		}
		return Item.SPAN_BAGGAGE_HEADER_PREFIX + TMHead.HEADER_DELIMITER + key;
	}

}
