package net.stealthcat.test.nio;

public interface Pipeline extends Handler{

	Pipeline addLast(String handlerName, Handler handler);
	
	Pipeline addFirst(String handlerName, Handler handler);
	
	Pipeline addBefore(String handlerName, Handler handler, String afterName);
	
	Pipeline addAfter(String handlerName, Handler handler, String beforeName);
	
	
}
