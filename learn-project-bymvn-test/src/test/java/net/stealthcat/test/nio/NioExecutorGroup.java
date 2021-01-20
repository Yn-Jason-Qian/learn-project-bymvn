package net.stealthcat.test.nio;


import net.stealthcat.util.CommonUtil;

import java.nio.channels.SelectableChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class NioExecutorGroup implements ExecutorGroup{
	private static final int EXECUTOR_COUNT = Runtime.getRuntime().availableProcessors() * 2;
	private NioExecutor[] executors;
	private Policy policy;
	private boolean shutdown;
	
	public NioExecutorGroup() {
		this(EXECUTOR_COUNT);
	}
	
	public NioExecutorGroup(int count) {
		executors = new NioExecutor[count];
		for(int i =0; i < count; i++) {
			executors[i] = new NioExecutor();
		}
		if(CommonUtil.isPowerOfTwo(count)) {
			policy = new PowerOfTwoPolicy(executors);
		} else {
			policy = new CommonPolicy(executors);
		}
	}
	
	private interface Policy {
		NioExecutor next();
	}
	
	private static class PowerOfTwoPolicy implements Policy{
		private AtomicInteger inc = new AtomicInteger();
		private NioExecutor[] executors;
		PowerOfTwoPolicy(NioExecutor[] executors) {
			this.executors = executors;
		}
		@Override
		public NioExecutor next() {
			return executors[inc.getAndIncrement() & executors.length - 1];
		}
	}
	
	private static class CommonPolicy implements Policy{
		private AtomicInteger inc = new AtomicInteger();
		private NioExecutor[] executors;
		CommonPolicy(NioExecutor[] executors) {
			this.executors = executors;
		}
		@Override
		public NioExecutor next() {
			return executors[Math.abs(inc.getAndIncrement() % executors.length)];
		}
	}

	@Override
	public Executor next() {
		return policy.next();
	}

	@Override
	public void register(SelectableChannel channel, int ops, Object attachment) {
		next().register(channel, ops, attachment);
	}

	@Override
	public void shutdown() {
		this.shutdown = true;
		for(NioExecutor executor : executors) {
			executor.shutdown();
		}
	}

	@Override
	public boolean isRunning() {
		return !shutdown;
	}
}
