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

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myproject.ms.monitoring.Item;


class THSResp extends HttpServletResponseWrapper {

	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	private final Item span;

	THSResp(HttpServletResponse response, Item span) {
		super(response);
		this.span = span;
	}

	@Override public void flushBuffer() throws IOException {
		if (log.isTraceEnabled()) {
			log.trace("Will annotate SS once the response is flushed");
		}
		SsLogSetter.annotateWithServerSendIfLogIsNotAlreadyPresent(this.span);
		super.flushBuffer();
	}

	@Override public ServletOutputStream getOutputStream() throws IOException {
		return new TSOStr(super.getOutputStream(), this.span);
	}

	@Override public PrintWriter getWriter() throws IOException {
		return new TPWriter(super.getWriter(), this.span);
	}
}
