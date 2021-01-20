package net.stealthcat.test.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class Test {

	private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    private static final int reCap = ~CAPACITY;
	
	public static void main(String args[]) throws InterruptedException {
//		testFullGcByCMS();
//		final Object lock = new Object();
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(true) {
////					synchronized (lock) {
////						try {
////							lock.wait();
////						} catch (InterruptedException e) {
////							e.printStackTrace();
////						}
////					}
//				}
//			}
//		}).start();
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				while(true) {
//					synchronized (lock) {
//						try {
//							lock.notify();
//							Thread.sleep(5);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}).start();
//		System.out.println(new Date(1473609600000L));
//		System.out.println(tableSizeFor(17));
//		System.out.println(1&7);
//		try {
//			throw new Exception();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			System.out.println("1");
//		}
		String a = "abc";
		String b = "abc";
		System.out.println(a==b);
	}
	
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
	
	public static void testMinorGC() throws InterruptedException {
		Thread.sleep(20000);
		byte[] byte0 = new byte[1024*1024*2];
		byte[] byte1 = new byte[1024*1024*2];
		byte[] byte2 = new byte[1024*1024*2];
		System.out.println("ready to direct allocate to old");
		Thread.sleep(3000);
		byte[] byte3 = new byte[1024*1024*4];
		Thread.sleep(3000);
	}
	
	public static void testFullGcByOldOver() throws InterruptedException {
//		Thread.sleep(20000);
		ArrayList<MemObject> list = new ArrayList<Test.MemObject>(10);
		for(int i=0;i<10;i++) {
			if(i == 6) {
				System.out.println("before minor gc");
			}
			list.add(new MemObject(1024*1024));
			if(i == 6) {
				System.out.println("after minor gc");
			}
		}
		System.gc();
		System.gc();
		System.out.println("before sleep");
		Thread.sleep(5000);
		System.out.println("before clear");
		list.clear();
		System.out.println("after clear");
		for(int i=0;i<10;i++) {
			if(i == 6) {
				System.out.println("before full gc");
			}
			list.add(new MemObject(1024*1024));
			if(i == 6) {
				System.out.println("after full gc");
			}
			if(i%3 == 0) {
				list.remove(0);
			}
		}
		System.out.println(list.size());
		Thread.sleep(5000);
	}
	
	public static void testFullGcByCMS() throws InterruptedException {
		ArrayList<MemObject> list = new ArrayList<Test.MemObject>(10);
		for(int i=0;i<6;i++) {
			list.add(new MemObject(1024*1024));
		}
		System.out.println("before minor gc");
		list.add(new MemObject(1024*1024*1));
		System.out.println("after minor gc");
		
		list.clear();
		Thread.sleep(5000);
		
		for(int i=0;i<4;i++) {
			list.add(new MemObject(2*1024*1024));
		}
		
		Thread.sleep(5000);
	}
	
	static class MemObject {
		byte bytes[];
		public MemObject(int size) {
			bytes = new byte[size];
		}
	}
	
	public static void testReference() throws InterruptedException {
		Object o = new Object();
//		Thread.sleep(20000);
		System.out.println("start");
//		SoftReference<Object> sr = new SoftReference<Object>(o);
		WeakReference<Object> r = new WeakReference<Object>(o, new ReferenceQueue<Object>());
		System.out.println(r.get());
		o = null;
		System.out.println(r.isEnqueued());
		Thread.sleep(3000);
		byte[] byte0 = new byte[1024*1024*4];
		byte[] byte1 = new byte[1024*1024*4];
		byte[] byte2 = new byte[1024*1024*3];
//		Thread.sleep(20000);
		System.out.println(r.isEnqueued());
		System.out.println("end");
		System.out.println(r.get());
	}
	
//	public static Void tt() {
//		System.out.println(reCap);
//		String account = "TEST";
//		String passwd = "123456";
//		String loginData = EtermString.generateLoginString(account, passwd);
//		loginData = "hello world";
//		System.out.println(loginData);
//		
//		String convertData = StringUtil.convertBytes2String(loginData.getBytes(Charset.forName("UTF-8")));
//		System.out.println(convertData);
//		System.out.println(loginData.equals(convertData));
//		return null;
//	}
	
	@org.junit.Test
	public void test1() {
		System.out.println(new Character((char) 5409));
	}
	
	@org.junit.Test
	public void test2() {
		for(int i=0;i<20;i++) {
			long start = System.nanoTime();
			long count = 0;
			for(int x=0;x<10700;x++) {
				count += x*i;
			}
			System.out.printf("index : %s, time : %s%n", i, System.nanoTime() - start);
		}
	}
	
	@org.junit.Test
	public void test3() {
		Executor executor = Executors.newFixedThreadPool(100);
		for(int i=0;i<100;i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					while(true) {
						try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
}
