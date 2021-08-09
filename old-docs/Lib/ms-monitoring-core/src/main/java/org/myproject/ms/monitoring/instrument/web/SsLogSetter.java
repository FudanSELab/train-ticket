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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Item;


class SsLogSetter {

	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	static void annotateWithServerSendIfLogIsNotAlreadyPresent(Item span) {
		if (span == null) {
			return;
		}
		for (org.myproject.ms.monitoring.Log log1 : span.logs()) {
			if (Item.SERVER_SEND.equals(log1.getEvent())) {
				if (log.isTraceEnabled()) {
					log.trace("Span was already annotated with SS, will not do it again");
				}
				return;
			}
		}
		if (log.isTraceEnabled()) {
			log.trace("Will set SS on the span");
		}
		span.logEvent(Item.SERVER_SEND);
	}
}
