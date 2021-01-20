package net.stealthcat.test.nio;


import net.stealthcat.util.LogUtil;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


public class NioExecutor implements Executor{
	private Selector selector;
	private boolean status;
	private static volatile int executeThreadCount;
	private Thread executeThread;
    
	public NioExecutor() {
		try {
			selector = Selector.open();
			executeThread = new ExecuteThread();
			executeThread.start();
			status = true;
		} catch (IOException e) {
			status = false;
			throw new RuntimeException("Failed to open a selector.", e);
		}
	}
	
	@Override
	public void register(SelectableChannel channel, int ops, Object attachment) {
		try {
			channel.register(selector, ops, attachment);
		} catch (ClosedChannelException e) {
			LogUtil.error("Channel has closed.", e);
			try {
				channel.close();
			} catch (IOException e1) {
				LogUtil.error("", e);
			}
		}
	}
	
	private class ExecuteThread extends Thread {
		
		ExecuteThread() {
			super("ExecuteThread" + executeThreadCount++);
		}

		@Override
		public void run() {
			while(status || Thread.interrupted()) {
				try {
					if(selector.selectNow() == 0) {
						try {
							TimeUnit.MILLISECONDS.sleep(10);
							continue;
						} catch (InterruptedException e) {
							LogUtil.error("", e);
							break;
						}
					}
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					for(; iterator.hasNext();) {
						SelectionKey key = iterator.next();
						iterator.remove();
						Object attachment = key.attachment();
						if (!key.isValid() && attachment instanceof NioClient) {
							((NioClient) attachment).close();
						}
						if(key.isConnectable()) {
							register(key.channel(), SelectionKey.OP_READ, attachment);
						} else if(key.isReadable()){
							((NioClient) attachment).doRead();
						} else if(key.isAcceptable()) {
							((NioServer) attachment).doAccept();
						}
					}
					
				} catch (IOException e) {
					LogUtil.error("", e);
				}
			}
		}
		
	}

	@Override
	public void shutdown() {
		status = false;
		executeThread.interrupt();
	}

	public boolean isRunning() {
		return status;
	}
}
