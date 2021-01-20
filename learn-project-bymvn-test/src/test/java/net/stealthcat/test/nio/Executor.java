package net.stealthcat.test.nio;

import java.nio.channels.SelectableChannel;

public interface Executor {

	void register(SelectableChannel channel, int ops, Object attachment);
	
	void shutdown();

	boolean isRunning();
}
