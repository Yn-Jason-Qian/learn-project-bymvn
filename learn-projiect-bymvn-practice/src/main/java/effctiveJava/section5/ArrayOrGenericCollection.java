package effctiveJava.section5;

import com.google.common.collect.Lists;

import java.util.List;

public class ArrayOrGenericCollection {

	public static void main(String[] args) {
		List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
		System.out.println(reduce(list, new Function<Integer>() {
			public Integer apply(Integer arg1, Integer arg2) {
				return arg1 + arg2;
			}
		}, 0));
	}
	
	static <T> T reduce(List<T> list, Function<T> func, T initValue) {
		@SuppressWarnings("unchecked")
		T[] snapshot = (T[]) list.toArray();
		Object[] objs = snapshot;
		T result = initValue;
		for(T t : snapshot) {
			result = func.apply(result, t);
		}
		return result;
	}
	
	static <T> void test() {
		T[] t = (T[]) new Object[10];
	}
}

interface Function<T> {
	T apply(T arg1, T arg2);
}