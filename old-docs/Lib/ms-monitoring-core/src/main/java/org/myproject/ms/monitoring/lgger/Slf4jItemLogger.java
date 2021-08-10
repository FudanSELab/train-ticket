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



package org.myproject.ms.monitoring.lgger;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.myproject.ms.monitoring.Item;


public class Slf4jItemLogger implements ItemLogger {

	private final Logger log;
	private final Pattern nameSkipPattern;

	public Slf4jItemLogger(String nameSkipPattern) {
		this.nameSkipPattern = Pattern.compile(nameSkipPattern);
		this.log = org.slf4j.LoggerFactory.getLogger(Slf4jItemLogger.class);
	}

	Slf4jItemLogger(String nameSkipPattern, Logger log) {
		this.nameSkipPattern = Pattern.compile(nameSkipPattern);
		this.log = log;
	}

	@Override
	public void logStartedSpan(Item parent, Item span) {
		MDC.put(Item.SPAN_ID_NAME, Item.idToHex(span.getSpanId()));
		MDC.put(Item.SPAN_EXPORT_NAME, String.valueOf(span.isExportable()));
		MDC.put(Item.TRACE_ID_NAME, span.traceIdString());
		log("Starting span: {}", span);
		if (parent != null) {
			log("With parent: {}", parent);
			MDC.put(Item.PARENT_ID_NAME, Item.idToHex(parent.getSpanId()));
		}
	}

	@Override
	public void logContinuedSpan(Item span) {
		MDC.put(Item.SPAN_ID_NAME, Item.idToHex(span.getSpanId()));
		MDC.put(Item.TRACE_ID_NAME, span.traceIdString());
		MDC.put(Item.SPAN_EXPORT_NAME, String.valueOf(span.isExportable()));
		setParentIdIfPresent(span);
		log("Continued span: {}", span);
	}

	private void setParentIdIfPresent(Item span) {
		if (!span.getParents().isEmpty()) {
			MDC.put(Item.PARENT_ID_NAME, Item.idToHex(span.getParents().get(0)));
		}
	}

	@Override
	public void logStoppedSpan(Item parent, Item span) {
		if (span != null) {
			log("Stopped span: {}", span);
		}
		if (span != null && parent != null) {
			log("With parent: {}", parent);
			MDC.put(Item.SPAN_ID_NAME, Item.idToHex(parent.getSpanId()));
			MDC.put(Item.SPAN_EXPORT_NAME, String.valueOf(parent.isExportable()));
			setParentIdIfPresent(parent);
		}
		else {
			MDC.remove(Item.SPAN_ID_NAME);
			MDC.remove(Item.SPAN_EXPORT_NAME);
			MDC.remove(Item.TRACE_ID_NAME);
			MDC.remove(Item.PARENT_ID_NAME);
		}
	}

	private void log(String text, Item span) {
		if (span != null && this.nameSkipPattern.matcher(span.getName()).matches()) {
			return;
		}
		if (this.log.isTraceEnabled()) {
			this.log.trace(text, span);
		}
	}

}
