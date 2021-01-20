package net.stealthcat.test.jvm;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectInvokeTest {
	private static final int WARMUP_COUNT = 10700;
	private ForReflection testObj = new ForReflection();
	private static Method method = null;
	
	public static void main(String[] args) throws Exception {
		method = ForReflection.class.getMethod("execute", String.class);
		ReflectInvokeTest test = new ReflectInvokeTest();
		for(int i=0;i<20;i++) {
			test.testCachedMethod();
			test.testNoCachedMethod();
			test.testRedirectExecute();
		}
		long start = System.currentTimeMillis();
		test.testRedirectExecute();
		long end = System.currentTimeMillis();
		System.out.printf("redirect execute spend time : %dm%n", end-start);
		start = System.currentTimeMillis();
		test.testCachedMethod();
		end = System.currentTimeMillis();
		System.out.printf("cached method execute spend time : %dm%n", end-start);
		start = System.currentTimeMillis();
		test.testNoCachedMethod();
		end = System.currentTimeMillis();
		System.out.printf("no cached method execute spend time : %dm%n", end-start);
	}
	
	public void testRedirectExecute() {
		for(int i = 0;i<WARMUP_COUNT;i ++) {
			testObj.execute("hello");
		}
	}
	
	public void testCachedMethod () throws Exception {
		for(int i=0;i<WARMUP_COUNT;i++) {
			method.invoke(testObj, "hello");
		}
	}
	
	public void testNoCachedMethod() throws Exception{
		for(int i=0;i<WARMUP_COUNT;i++) {
			Method noCachedMethod = ForReflection.class.getMethod("execute", String.class);
			noCachedMethod.invoke(testObj, "hello");
		}
	}

	public class ForReflection {
		private Map<String, String> caches = new HashMap<String, String>();
		
		public void execute(String message) {
			String key = this.toString() + message;
			caches.put(key, message);
		}
	}
}
