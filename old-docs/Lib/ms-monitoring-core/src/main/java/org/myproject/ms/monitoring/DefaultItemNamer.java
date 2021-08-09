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



package org.myproject.ms.monitoring;

import org.springframework.core.annotation.AnnotationUtils;


public class DefaultItemNamer implements ItemNamer {

	@Override
	public String name(Object object, String defaultValue) {
		ItemName annotation = AnnotationUtils
				.findAnnotation(object.getClass(), ItemName.class);
		String spanName = annotation != null ? annotation.value() : object.toString();
		// If there is no overridden toString method we'll put a constant value
		if (isDefaultToString(object, spanName)) {
			return defaultValue;
		}
		return spanName;
	}

	private static boolean isDefaultToString(Object delegate, String spanName) {
		return (delegate.getClass().getName() + "@" +
				Integer.toHexString(delegate.hashCode())).equals(spanName);
	}
}
