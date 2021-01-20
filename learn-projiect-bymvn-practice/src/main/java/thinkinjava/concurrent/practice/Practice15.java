package thinkinjava.concurrent.practice;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Practice15 {

	static class Method {
		private static List<Object> locks = Lists.newArrayList();
		private static List<ReentrantLock> locks2 = Lists.newArrayList();
		static {
			locks.add(new Object());
			locks.add(new Object());
			locks.add(new Object());
			locks2.add(new ReentrantLock());
			locks2.add(new ReentrantLock());
			locks2.add(new ReentrantLock());
		}
		void method1() {
			synchronized (this) {
				for(int i = 0; i < 5; i++) {
					System.out.println("method1 invoke, count : " + i);
				}
			}
		}
		
		void method2() {
			synchronized (this) {
				for(int i = 0; i < 5; i++) {
					System.out.println("method2 invoke, count : " + i);
				}
			}
		}
		
		void method3() {
			synchronized (this) {
				for(int i = 0; i < 5; i++) {
					System.out.println("method3 invoke, count : " + i);
				}
			}
		}
	}
	
	static class Task1 implements Runnable {
		private Method method;
		
		public Task1(Method method) {
			this.method = method;
		}

		@Override
		public void run() {
			method.method1();
		}
		
	}
	
	static class Task2 implements Runnable {
		private Method method;
		
		public Task2(Method method) {
			this.method = method;
		}

		@Override
		public void run() {
			method.method2();
		}
		
	}
	
	static class Task3 implements Runnable {
		private Method method;
		
		public Task3(Method method) {
			this.method = method;
		}

		@Override
		public void run() {
			method.method3();
		}
		
	}
	
	public static void main(String[] args) {
		Method method = new Method();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Task1(method));
		service.execute(new Task2(method));
		service.execute(new Task3(method));
		service.shutdown();
	}

}
