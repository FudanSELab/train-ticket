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

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ItemTextMap;
import org.myproject.ms.monitoring.util.TextMapUtil;
import org.springframework.util.StringUtils;


public class ZHSExtra implements HSExtra {

	private static final org.apache.commons.logging.Log log = LogFactory.getLog(
			MethodHandles.lookup().lookupClass());
	private static final String HEADER_DELIMITER = "-";
	static final String URI_HEADER = "X-Span-Uri";
	private static final String HTTP_COMPONENT = "http";

	private final Pattern skipPattern;

	public ZHSExtra(Pattern skipPattern) {
		this.skipPattern = skipPattern;
	}

	@Override
	public Item joinTrace(ItemTextMap textMap) {
		Map<String, String> carrier = TextMapUtil.asMap(textMap);
		boolean debug = Item.SPAN_SAMPLED.equals(carrier.get(Item.SPAN_FLAGS));
		if (debug) {
			// we're only generating Trace ID since if there's no Span ID will assume
			// that it's equal to Trace ID
			generateIdIfMissing(carrier, Item.TRACE_ID_NAME);
		} else if (carrier.get(Item.TRACE_ID_NAME) == null) {
			// can't build a Span without trace id
			return null;
		}
		try {
			String uri = carrier.get(URI_HEADER);
			boolean skip = this.skipPattern.matcher(uri).matches()
					|| Item.SPAN_NOT_SAMPLED.equals(carrier.get(Item.SAMPLED_NAME));
			long spanId = spanId(carrier);
			return buildParentSpan(carrier, uri, skip, spanId);
		} catch (Exception e) {
			log.error("Exception occurred while trying to extract span from carrier", e);
			return null;
		}
	}

	private void generateIdIfMissing(Map<String, String> carrier, String key) {
		if (!carrier.containsKey(key)) {
			carrier.put(key, Item.idToHex(new Random().nextLong()));
		}
	}

	private long spanId(Map<String, String> carrier) {
		String spanId = carrier.get(Item.SPAN_ID_NAME);
		if (spanId == null) {
			if (log.isDebugEnabled()) {
				log.debug("Request is missing a span id but it has a trace id. We'll assume that this is "
						+ "a root span with span id equal to the lower 64-bits of the trace id");
			}
			return Item.hexToId(carrier.get(Item.TRACE_ID_NAME));
		} else {
			return Item.hexToId(spanId);
		}
	}

	private Item buildParentSpan(Map<String, String> carrier, String uri, boolean skip, long spanId) {
		String traceId = carrier.get(Item.TRACE_ID_NAME);
		Item.SpanBuilder span = Item.builder()
				.traceIdHigh(traceId.length() == 32 ? Item.hexToId(traceId, 0) : 0)
				.traceId(Item.hexToId(traceId))
				.spanId(spanId);
		String processId = carrier.get(Item.PROCESS_ID_NAME);
		String parentName = carrier.get(Item.SPAN_NAME_NAME);
		if (StringUtils.hasText(parentName)) {
			span.name(parentName);
		}  else {
			span.name(HTTP_COMPONENT + ":/parent" + uri);
		}
		if (StringUtils.hasText(processId)) {
			span.processId(processId);
		}
		if (carrier.containsKey(Item.PARENT_ID_NAME)) {
			span.parent(Item.hexToId(carrier.get(Item.PARENT_ID_NAME)));
		}
		span.remote(true);
		boolean debug = Item.SPAN_SAMPLED.equals(carrier.get(Item.SPAN_FLAGS));
		if (debug) {
			span.exportable(true);
		} else if (skip) {
			span.exportable(false);
		}
		for (Map.Entry<String, String> entry : carrier.entrySet()) {
			if (entry.getKey().startsWith(Item.SPAN_BAGGAGE_HEADER_PREFIX + HEADER_DELIMITER)) {
				span.baggage(unprefixedKey(entry.getKey()), entry.getValue());
			}
		}
		return span.build();
	}

	private String unprefixedKey(String key) {
		return key.substring(key.indexOf(HEADER_DELIMITER) + 1);
	}

}
