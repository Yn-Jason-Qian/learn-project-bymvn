package net.stealthcat.test.jvm;

import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class StackByCasTest {
	
	static class ConcurrentStack<T> {
		private AtomicReference<Node> head = new AtomicReference<Node>();
		
		public void push(T t) {
			Node oldHead = null;
			Node newHead = new Node(t);
			do {
				oldHead = head.get();
				newHead.next = oldHead;
			} while(!head.compareAndSet(oldHead, newHead));
		}
		
		public T pop() {
			Node oldHead = null;
			Node newHead = null;
			do {
				oldHead = head.get();
				if(oldHead != null) {
					newHead = oldHead.next;
				}
			} while(!head.compareAndSet(oldHead, newHead));
			return oldHead.value;
		}
		
		class Node {
			final T value;
			Node next;
			Node(T t) {
				value = t;
			}
		}
	}

	static int threadCount = 100;
	static CountDownLatch latch;
	static int executeTimes = 10;
	
	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		long start2;
		latch = new CountDownLatch(threadCount);
		for(int i=0;i<executeTimes;i++) {
			for(int x=0;x<threadCount;x++) {
				new Thread(new Task(Task.STACK_MODE)).start();
			}
		}
		latch.await();
		System.out.printf("Java Stack execute time : %s%n", (start2 = System.currentTimeMillis()) - start);
		
		latch = new CountDownLatch(threadCount);
		for(int i=0;i<executeTimes;i++) {
			for(int x=0;x<threadCount;x++) {
				new Thread(new Task(Task.CON_MODE)).start();
			}
		}
		latch.await();
		System.out.printf("Con Stack execute time : %s%n", System.currentTimeMillis() - start2);
	}
	
	static Stack<String> stack = new Stack<String>();
	
	static ConcurrentStack<String> conStack = new ConcurrentStack<String>();
	
	static int pushAndPopTimes = 10;
	
	static class Task implements Runnable {
		
		final static int STACK_MODE = 0;
		final static int CON_MODE = 1;
		int mode;
		Task(int mode) {
			this.mode = mode;
		}
		
		@Override
		public void run() {
			for(int i=0;i<pushAndPopTimes;i++) {
				if(mode == STACK_MODE) {
					stack.push(Thread.currentThread().getName() + i);
					stack.pop();
				}
			}
			latch.countDown();
		}
		
	}

}
