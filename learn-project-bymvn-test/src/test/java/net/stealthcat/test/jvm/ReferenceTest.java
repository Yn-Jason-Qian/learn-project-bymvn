package net.stealthcat.test.jvm;

import java.util.Map;
import java.util.WeakHashMap;


public class ReferenceTest {

	private static class MemObject {
		byte[] bytes = new byte[1024 * 1024];
	}
	public static void main(String[] args) throws InterruptedException {
		Map<String, MemObject> map = new WeakHashMap<String, MemObject>();
		for(int i=0;i<20;i++) {
			map.put("" + i, new MemObject());
		}
		System.gc();
//		System.out.println(ref.get());
		System.gc();
		System.out.println(map.size());
		System.out.println(map.values());
		Thread.sleep(200000);
	}

}
