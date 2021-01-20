package thinkinjava.generics;

import com.common.impls.ColoredImpl;
import com.common.impls.SerialNumberedImpl;
import com.common.impls.TimestampImpl;
import com.common.interfaces.Colored;
import com.common.interfaces.SerialNumbered;
import com.common.interfaces.Timestamped;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import static thinkinjava.generics.DynamicProxyMixin.Pair.tuple;

/*
 * 动态代理实现混型
 */
public class DynamicProxyMixin {
	
	static class DynamicProxy implements InvocationHandler {

		Map<String, Object> delegatesByMethod = Maps.newHashMap();
		
		@SafeVarargs
		public DynamicProxy(Pair<Object, Class<?>>... pairs) {
			for(Pair<Object, Class<?>> pair : pairs) {
				Method[] methods = pair.second.getMethods();
				for(Method method : methods) {
					if(!delegatesByMethod.containsKey(method.getName())) {
						delegatesByMethod.put(method.getName(), pair.first);
					}
				}
			}
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object obj = delegatesByMethod.get(method.getName());
			return method.invoke(obj, args);
		}
		
		@SafeVarargs
		public static Object newProxy(Pair<Object, Class<?>>... pairs) {
			Class[] interfaces = new Class[pairs.length];
			for(int i=0;i<pairs.length;i++) {
				interfaces[i] = pairs[i].second;
			}
			return Proxy.newProxyInstance(pairs[0].second.getClassLoader(), interfaces, new DynamicProxy(pairs));
		}
		
	}
	
	//包下SelfBounding.java文件也有F类，所以这里有警告
	static class Pair<F, S>{
		F first;
		S second;
		public Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}
		static <F, S> Pair<F, S> tuple(F first,S second) {
			return new Pair<>(first, second);
		}
	}
	
	public static void main(String[] args) {
		Object proxy = DynamicProxy.newProxy(tuple(new TimestampImpl(), Timestamped.class), 
				tuple(new SerialNumberedImpl(), SerialNumbered.class),
				tuple(new ColoredImpl(), Colored.class));
		Timestamped t = (Timestamped) proxy;
		System.out.println(t.getTimestamp());
		
		Colored c = (Colored) proxy;
		c.setColor("red");
		System.out.println(c.getColor());
		
		SerialNumbered s = (SerialNumbered) proxy;
		System.out.println(s.getSerialNumber());
	}
	
}
