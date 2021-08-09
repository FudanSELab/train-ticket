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

package org.myproject.ms.monitoring.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;


public final class TextMapUtil {

	private TextMapUtil() {}

	public static Map<String, String> asMap(Iterable<Map.Entry<String, String>> iterable) {
		Map<String, String> map = new TreeMap<>(new Comparator<String>() {
			@Override public int compare(String o1, String o2) {
				return o1.toLowerCase().compareTo(o2.toLowerCase());
			}
		});
		for (Map.Entry<String, String> entry : iterable) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
}
