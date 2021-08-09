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

import org.springframework.util.StringUtils;


public final class ItemNameUtil {

	static final int MAX_NAME_LENGTH = 50;

	public static String shorten(String name) {
		if (StringUtils.isEmpty(name)) {
			return name;
		}
		int maxLength = name.length() > MAX_NAME_LENGTH ? MAX_NAME_LENGTH : name.length();
		return name.substring(0, maxLength);
	}

	public static String toLowerHyphen(String name) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i != 0) result.append('-');
				result.append(Character.toLowerCase(c));
			} else {
				result.append(c);
			}
		}
		return ItemNameUtil.shorten(result.toString());
	}
}
