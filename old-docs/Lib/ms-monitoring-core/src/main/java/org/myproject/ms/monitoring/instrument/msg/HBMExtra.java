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

import java.util.Map;
import java.util.Random;

import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemTextMap;
import org.myproject.ms.monitoring.util.TextMapUtil;


public class HBMExtra implements MSTMExtra {

	@Override
	public Item joinTrace(ItemTextMap textMap) {
		Map<String, String> carrier = TextMapUtil.asMap(textMap);
		if (Item.SPAN_SAMPLED.equals(carrier.get(TMHead.SPAN_FLAGS_NAME))) {
			String traceId = generateTraceIdIfMissing(carrier);
			if (!carrier.containsKey(TMHead.SPAN_ID_NAME)) {
				carrier.put(TMHead.SPAN_ID_NAME, traceId);
			}
		} else if (!hasHeader(carrier, TMHead.SPAN_ID_NAME)
				|| !hasHeader(carrier, TMHead.TRACE_ID_NAME)) {
			return null;
			// TODO: Consider throwing IllegalArgumentException;
		}
		return extractSpanFromHeaders(carrier, Item.builder());
	}

	private String generateTraceIdIfMissing(Map<String, String> carrier) {
		if (!hasHeader(carrier, TMHead.TRACE_ID_NAME)) {
			carrier.put(TMHead.TRACE_ID_NAME, Item.idToHex(new Random().nextLong()));
		}
		return carrier.get(TMHead.TRACE_ID_NAME);
	}

	private Item extractSpanFromHeaders(Map<String, String> carrier, Item.SpanBuilder spanBuilder) {
		String traceId = carrier.get(TMHead.TRACE_ID_NAME);
		spanBuilder = spanBuilder
				.traceIdHigh(traceId.length() == 32 ? Item.hexToId(traceId, 0) : 0)
				.traceId(Item.hexToId(traceId))
				.spanId(Item.hexToId(carrier.get(TMHead.SPAN_ID_NAME)));
		String flags = carrier.get(TMHead.SPAN_FLAGS_NAME);
		if (Item.SPAN_SAMPLED.equals(flags)) {
			spanBuilder.exportable(true);
		} else {
			spanBuilder.exportable(
				Item.SPAN_SAMPLED.equals(carrier.get(TMHead.SAMPLED_NAME)));
		}
		String processId = carrier.get(TMHead.PROCESS_ID_NAME);
		String spanName = carrier.get(TMHead.SPAN_NAME_NAME);
		if (spanName != null) {
			spanBuilder.name(spanName);
		}
		if (processId != null) {
			spanBuilder.processId(processId);
		}
		setParentIdIfApplicable(carrier, spanBuilder, TMHead.PARENT_ID_NAME);
		spanBuilder.remote(true);
		for (Map.Entry<String, String> entry : carrier.entrySet()) {
			if (entry.getKey().startsWith(Item.SPAN_BAGGAGE_HEADER_PREFIX + TMHead.HEADER_DELIMITER)) {
				spanBuilder.baggage(unprefixedKey(entry.getKey()), entry.getValue());
			}
		}
		return spanBuilder.build();
	}

	boolean hasHeader(Map<String, String> message, String name) {
		return message.containsKey(name);
	}

	private void setParentIdIfApplicable(Map<String, String> carrier, Item.SpanBuilder spanBuilder,
			String spanParentIdHeader) {
		String parentId = carrier.get(spanParentIdHeader);
		if (parentId != null) {
			spanBuilder.parent(Item.hexToId(parentId));
		}
	}

	private String unprefixedKey(String key) {
		return key.substring(key.indexOf(TMHead.HEADER_DELIMITER) + 1);
	}

}
