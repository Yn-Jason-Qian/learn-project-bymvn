package net.stealthcat.test.jvm;

public class GCTest {

	public static void main(String[] args) throws InterruptedException {
		testMaxTenuringThreshold();

	}
	
	public static void testMaxTenuringThreshold() throws InterruptedException {
		Thread.sleep(50000);
		byte[] b1 = new byte[1024*1024/4];
		byte[] b2 = new byte[1024*1024*4];
		System.out.println("pre to minor gc");
		byte[] b3 = new byte[1024*1024*3 + 900 * 1024];
		b3 = null;
		System.out.println("pre to minor gc twice");
		b3 = new byte[1024*1024*4];
		Thread.sleep(10000);
	}
	
	public static void testHandlePromotionFailure() {
		
	}

}
