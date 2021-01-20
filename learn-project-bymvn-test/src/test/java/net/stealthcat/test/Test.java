package net.stealthcat.test;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Parent {
	public int i = 10;
}

class Child extends Parent {
	public int i = 20;
}

public class Test {
	
	static Integer num;

	final static public void main(String[] args) throws ClassNotFoundException, UnsupportedEncodingException, InterruptedException {
		System.out.println(ticketmark.length());
		System.out.println(num);
		Parent p = new Child();
		Child c = (Child) p;
		System.out.println(p.i + c.i);
		System.out.println(block());
		
		ExecutorService executor = Executors.newCachedThreadPool();
		sb.acquire();
		executor.execute(new TaskA());
		Thread.sleep(10);
		executor.execute(new TaskB());

		Thread.sleep(100);
		System.exit(0);
		
		
	}
	
	static String block() {
		String i = "a";
		try {
			System.out.println("try block");
//			int a = 100 / 0;
		} catch (Exception e) {
			System.out.println("return block");
			i = "b";
			
			return i;
		} finally {
			System.out.println("finally block");
			i = "c";
//			return i;
		}
		return i;
	}
	
	static void test1() {
		System.out.println(System.getProperty("jdbc.drivers"));
	}

	static String ticketmark = "凡改期,改签,跨月签转或者升舱,如产生代理费差异,需全部收回.订座记录中必须预留旅客手机号码，未按要求留存旅客有效联系方式或输入的旅客联系电";
	

	static Semaphore sa = new Semaphore(1);
	static Semaphore sb = new Semaphore(1);
	static class A implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					sa.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.print("a");
				sb.release();
			}
		}
		
	}
	
	static class B implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					sb.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("b");
				sa.release();
			}
		}
		
	}
	
	static Object lock = new Object();
	static class TaskA implements Runnable {

		@Override
		public void run() {
			while(true) {
				synchronized(lock) {
					System.out.print("A");
					lock.notify();
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	static class TaskB implements Runnable {

		@Override
		public void run() {
			while(true) {
				synchronized (lock) {
					System.out.println("B");
					lock.notify();
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
