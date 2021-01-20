package net.stealthcat.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

	public static void main(String[] args) {
		new ProxyTest().proxy();
	}
	
	void proxy() {
		new A().proxy();
	}
}

class A {
	
	public void proxy() {
		IInterface inter = (IInterface) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{IInterface.class}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("proxy test：" + this.getClass());
				return null;
			}
		});
		inter.test();
	}
	
}