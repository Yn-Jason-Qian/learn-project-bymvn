package net.stealthcat.test.jvm;

import java.util.ArrayList;

public class JvmMemTest {

	public static void main(String[] args) throws InterruptedException {
		testMinorGCAndOldFull();
		
	}
	
	static void testMemUsed() throws InterruptedException {
//		Thread.sleep(20000);
		byte[] bytes = new byte[100*1024*1024];
//		bytes[0] = 1;
//		bytes[1] = 2;
		
//		Thread.sleep(10000);
		
//		ByteBuffer buffer = ByteBuffer.allocateDirect(100*1024*1024);
//		buffer.put(bytes);
		
//		Thread.sleep(10000);
		
//		bytes = null;
//		buffer.flip();
//		System.gc();
		
//		Thread.sleep(10000);
		
		bytes = new byte[100*1024*1024];
	//	buffer.get(bytes);
		
		Thread.sleep(10000);
		
		bytes = null;
//		buffer = null;
		System.gc();
		System.gc();
		
		Thread.sleep(1000000);
	}
	
	//测试minorGC时，如果Old满了会怎么样
	static void testMinorGCAndOldFull() throws InterruptedException {
		Thread.sleep(30000);
		ArrayList<MemObject> list = new ArrayList<MemObject>();
		for(int i=0;i<51200;i++) {
			list.add(new MemObject(2));
		}
		Thread.sleep(10000);
		System.gc();
		list = null;
		System.out.println("ready to gc");
		Thread.sleep(10000);
		ArrayList<MemObject> temp = new ArrayList<JvmMemTest.MemObject>();
		for(int i=0;i<3 * 1024;i++) {
			temp.add(new MemObject(5));
		}
//		temp = null;
	}
	
	static class MemObject {
		byte[] bytes;
		MemObject(int size) {
			this.bytes = new byte[size * 1024];
		}
	}
}
