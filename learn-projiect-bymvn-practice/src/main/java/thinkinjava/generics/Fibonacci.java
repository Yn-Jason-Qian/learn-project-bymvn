package thinkinjava.generics;

import com.google.common.collect.Lists;

import java.util.List;


public class Fibonacci implements Generator<Integer>{

	private final List<Integer> cache = Lists.newArrayList(0, 1, 1);
	
	private Integer fibonacci (Integer n) {
		if(n <= 2) {
			return cache.get(n);
		}
		Integer fib1 = 0, fib2 = 0, fib3;
		if(n - 2 < cache.size()) {
			fib1 = cache.get(n - 2);
			if(n - 1 < cache.size()) {
				fib2 = cache.get(n - 1);
			}
		}
		if(fib1 == 0) {
			fib1 = fibonacci(n - 2);
		}
		if(fib2 == 0) {
			fib2 = fibonacci(n - 1);
		}
		fib3 = fib1 + fib2;
		cache.add(fib3);
		return fib3;
	}
	
	private int cursor = 1;
	
	@Override
	public Integer next() {
		return fibonacci(cursor++);
	}
	
	public static void main(String[] args) {
		Generator<Integer> gen = new Fibonacci();
		for(int i=0;i<18;i++) {
			System.out.println(gen.next());
		}
		
		System.out.println(new Fibonacci().fibonacci(12));
	}

}
