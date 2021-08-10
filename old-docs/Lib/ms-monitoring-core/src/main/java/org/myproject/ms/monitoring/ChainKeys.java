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

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("spring.sleuth.keys")
public class ChainKeys {

	private Http http = new Http();

	private Message message = new Message();

	private Hystrix hystrix = new Hystrix();

	private Async async = new Async();

	private Mvc mvc = new Mvc();

	public Http getHttp() {
		return this.http;
	}

	public Message getMessage() {
		return this.message;
	}

	public Hystrix getHystrix() {
		return this.hystrix;
	}

	public Async getAsync() {
		return this.async;
	}

	public Mvc getMvc() {
		return this.mvc;
	}

	public void setHttp(Http http) {
		this.http = http;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setHystrix(Hystrix hystrix) {
		this.hystrix = hystrix;
	}

	public void setAsync(Async async) {
		this.async = async;
	}

	public void setMvc(Mvc mvc) {
		this.mvc = mvc;
	}

	public static class Message {

		private Payload payload = new Payload();

		public Payload getPayload() {
			return this.payload;
		}

		public String getPrefix() {
			return this.prefix;
		}

		public Collection<String> getHeaders() {
			return this.headers;
		}

		public void setPayload(Payload payload) {
			this.payload = payload;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public void setHeaders(Collection<String> headers) {
			this.headers = headers;
		}

		public static class Payload {
			
			private String size = "message/payload-size";
			
			private String type = "message/payload-type";

			public String getSize() {
				return this.size;
			}

			public String getType() {
				return this.type;
			}

			public void setSize(String size) {
				this.size = size;
			}

			public void setType(String type) {
				this.type = type;
			}
		}

		
		private String prefix = "message/";

		
		private Collection<String> headers = new LinkedHashSet<String>();

	}

	public static class Http {

		
		private String host = "http.host";

		
		private String method = "http.method";

		
		private String path = "http.path";

		
		private String url = "http.url";

		
		private String statusCode = "http.status_code";

		
		private String requestSize = "http.request.size";

		
		private String responseSize = "http.response.size";

		
		private String prefix = "http.";

		
		private Collection<String> headers = new LinkedHashSet<String>();

		public String getHost() {
			return this.host;
		}

		public String getMethod() {
			return this.method;
		}

		public String getPath() {
			return this.path;
		}

		public String getUrl() {
			return this.url;
		}

		public String getStatusCode() {
			return this.statusCode;
		}

		public String getRequestSize() {
			return this.requestSize;
		}

		public String getResponseSize() {
			return this.responseSize;
		}

		public String getPrefix() {
			return this.prefix;
		}

		public Collection<String> getHeaders() {
			return this.headers;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public void setRequestSize(String requestSize) {
			this.requestSize = requestSize;
		}

		public void setResponseSize(String responseSize) {
			this.responseSize = responseSize;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public void setHeaders(Collection<String> headers) {
			this.headers = headers;
		}
	}

	
	public static class Hystrix {

		
		private String prefix = "";

		
		private String commandKey = "commandKey";

		
		private String commandGroup = "commandGroup";

		
		private String threadPoolKey = "threadPoolKey";

		public String getPrefix() {
			return this.prefix;
		}

		public String getCommandKey() {
			return this.commandKey;
		}

		public String getCommandGroup() {
			return this.commandGroup;
		}

		public String getThreadPoolKey() {
			return this.threadPoolKey;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public void setCommandKey(String commandKey) {
			this.commandKey = commandKey;
		}

		public void setCommandGroup(String commandGroup) {
			this.commandGroup = commandGroup;
		}

		public void setThreadPoolKey(String threadPoolKey) {
			this.threadPoolKey = threadPoolKey;
		}
	}

	
	public static class Async {

		
		private String prefix = "";

		
		private String threadNameKey = "thread";

		
		private String classNameKey = "class";

		
		private String methodNameKey = "method";

		public String getPrefix() {
			return this.prefix;
		}

		public String getThreadNameKey() {
			return this.threadNameKey;
		}

		public String getClassNameKey() {
			return this.classNameKey;
		}

		public String getMethodNameKey() {
			return this.methodNameKey;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public void setThreadNameKey(String threadNameKey) {
			this.threadNameKey = threadNameKey;
		}

		public void setClassNameKey(String classNameKey) {
			this.classNameKey = classNameKey;
		}

		public void setMethodNameKey(String methodNameKey) {
			this.methodNameKey = methodNameKey;
		}
	}

	
	public static class Mvc {

		
		private String controllerClass = "mvc.controller.class";

		
		private String controllerMethod = "mvc.controller.method";

		public String getControllerClass() {
			return this.controllerClass;
		}

		public void setControllerClass(String controllerClass) {
			this.controllerClass = controllerClass;
		}

		public String getControllerMethod() {
			return this.controllerMethod;
		}

		public void setControllerMethod(String controllerMethod) {
			this.controllerMethod = controllerMethod;
		}
	}

}
