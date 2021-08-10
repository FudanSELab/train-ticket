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


public class TMHead {

	public static final String SPAN_ID_NAME = "spanId";
	public static final String SAMPLED_NAME = "spanSampled";
	public static final String PROCESS_ID_NAME = "spanProcessId";
	public static final String PARENT_ID_NAME = "spanParentSpanId";
	public static final String TRACE_ID_NAME = "spanTraceId";
	public static final String SPAN_NAME_NAME = "spanName";
	public static final String SPAN_FLAGS_NAME = "spanFlags";

	static final String MESSAGE_SENT_FROM_CLIENT = "messageSent";
	static final String HEADER_DELIMITER = "_";

	private TMHead() {}
}
