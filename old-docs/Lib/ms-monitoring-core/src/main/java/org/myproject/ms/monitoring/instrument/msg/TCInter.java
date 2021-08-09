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

import org.myproject.ms.monitoring.Log;
import org.myproject.ms.monitoring.Item;
import org.myproject.ms.monitoring.ChainKeys;
import org.myproject.ms.monitoring.Chainer;
import org.myproject.ms.monitoring.spl.NeverSampler;
import org.myproject.ms.monitoring.util.ExceptionUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;


public class TCInter extends ATCInter {

	public TCInter(Chainer tracer, ChainKeys traceKeys,
			MSTMExtra spanExtractor,
			MSTMInject spanInjector) {
		super(tracer, traceKeys, spanExtractor, spanInjector);
	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		Item currentSpan = getTracer().getCurrentSpan();
		if (containsServerReceived(currentSpan)) {
			currentSpan.logEvent(Item.SERVER_SEND);
		} else if (currentSpan != null) {
			currentSpan.logEvent(Item.CLIENT_RECV);
		}
		addErrorTag(ex);
		getTracer().close(currentSpan);
	}

	private boolean containsServerReceived(Item span) {
		if (span == null) {
			return false;
		}
		for (Log log : span.logs()) {
			if (Item.SERVER_RECV.equals(log.getEvent())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		MessageBuilder<?> messageBuilder = MessageBuilder.fromMessage(message);
		Item parentSpan = getTracer().isTracing() ? getTracer().getCurrentSpan()
				: buildSpan(new MTMap(messageBuilder));
		String name = getMessageChannelName(channel);
		Item span = startSpan(parentSpan, name, message);
		if (message.getHeaders().containsKey(TMHead.MESSAGE_SENT_FROM_CLIENT)) {
			span.logEvent(Item.SERVER_RECV);
		} else {
			span.logEvent(Item.CLIENT_SEND);
			messageBuilder.setHeader(TMHead.MESSAGE_SENT_FROM_CLIENT, true);
		}
		getSpanInjector().inject(span, new MTMap(messageBuilder));
		MessageHeaderAccessor headers = MessageHeaderAccessor.getMutableAccessor(message);
		headers.copyHeaders(messageBuilder.build().getHeaders());
		return new GenericMessage<Object>(message.getPayload(), headers.getMessageHeaders());
	}

	private Item startSpan(Item span, String name, Message<?> message) {
		if (span != null) {
			return getTracer().createSpan(name, span);
		}
		if (Item.SPAN_NOT_SAMPLED.equals(message.getHeaders().get(TMHead.SAMPLED_NAME))) {
			return getTracer().createSpan(name, NeverSampler.INSTANCE);
		}
		return getTracer().createSpan(name);
	}

	@Override
	public Message<?> beforeHandle(Message<?> message, MessageChannel channel,
			MessageHandler handler) {
		Item spanFromHeader = getTracer().getCurrentSpan();
		if (spanFromHeader!= null) {
			spanFromHeader.logEvent(Item.SERVER_RECV);
		}
		getTracer().continueSpan(spanFromHeader);
		return message;
	}

	@Override
	public void afterMessageHandled(Message<?> message, MessageChannel channel,
			MessageHandler handler, Exception ex) {
		Item spanFromHeader = getTracer().getCurrentSpan();
		if (spanFromHeader!= null) {
			spanFromHeader.logEvent(Item.SERVER_SEND);
			addErrorTag(ex);
		}
		// related to #447
		if (getTracer().isTracing()) {
			getTracer().detach(spanFromHeader);
		}
	}

	private void addErrorTag(Exception ex) {
		if (ex != null) {
			getTracer().addTag(Item.SPAN_ERROR_TAG_NAME, ExceptionUtils.getExceptionMessage(ex));
		}
	}

}
