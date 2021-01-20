package algorithm.section2;

import java.util.ArrayList;
import java.util.List;

public class Test002_1 {

	static List<Integer> cache = new ArrayList<Integer>();
	static{
		cache.add(1);
		cache.add(1);
	}
	static long factorial(int n) {
		if(n <= cache.size()) {
			Integer value = cache.get(n - 1);
			return value.longValue();
		} else {
			long fn = factorial(n - 1) + factorial(n - 2);
			cache.add((int)fn);
			return fn;
		}
	}
	
	public static void main(String[] args) {
		for(int i=0; i<=10; i++) {
			System.out.println(factorial(i+3));
		}
	}

}
