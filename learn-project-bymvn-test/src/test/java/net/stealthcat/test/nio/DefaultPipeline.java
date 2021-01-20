package net.stealthcat.test.nio;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public class DefaultPipeline implements Pipeline{

	private HandlerContext head = new DefaultHandlerContext(null);

	private HandlerContext tail = new DefaultHandlerContext(null);

	private static ConcurrentMap<String, HandlerContext> contexts = Maps.newConcurrentMap();

	@Override
	public void doMessageRead(byte[] data, Client client) {
		if(head != null) {
			head.fireChannelRead(data, client);
		}
	}

	@Override
	public Pipeline addLast(String handlerName, Handler handler) {
		DefaultHandlerContext handlerContext = new DefaultHandlerContext(handler);
		contexts.put(handlerName, handlerContext);
		if(tail != null) {
			tail.setAfter(handlerContext);
			handlerContext.setBefore(tail);
		}
		tail = handlerContext;
		return this;
	}

	@Override
	public Pipeline addFirst(String handlerName, Handler handler) {
		DefaultHandlerContext handlerContext = new DefaultHandlerContext(handler);
		contexts.put(handlerName, handlerContext);
		if(head != null) {
			head.setBefore(handlerContext);
			handlerContext.setAfter(head);
		}
		head = handlerContext;
		return this;
	}

	@Override
	public Pipeline addBefore(String handlerName, Handler handler, String afterName) {
		HandlerContext afterContext = contexts.get(afterName);
		if(afterContext == null) {
			throw new IllegalArgumentException(String.format("Handler of %s is null.", afterName));
		}
		DefaultHandlerContext handlerContext = new DefaultHandlerContext(handler);
		contexts.put(handlerName, handlerContext);
		HandlerContext before = afterContext.getBefore();

		handlerContext.setBefore(before);
		handlerContext.setAfter(afterContext);

		before.setAfter(handlerContext);
		afterContext.setBefore(handlerContext);
		return null;
	}

	@Override
	public Pipeline addAfter(String handlerName, Handler handler, String beforeName) {
		return null;
	}

	@Override
	public boolean isRead() {
		return false;
	}

}
