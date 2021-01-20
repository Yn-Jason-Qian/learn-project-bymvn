package net.stealthcat.test.nio;

public interface ExecutorGroup extends Executor{

	Executor next();
	
}
