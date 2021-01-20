package thinkinjava.generics;

import java.util.Iterator;

public class IterableFibonacci implements Iterable<Integer> {

	private Fibonacci fib;
	private int n;
	
	public IterableFibonacci(Fibonacci fib, int n) {
		this.fib = fib;
		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new FibonacciIterator(n);
	}
	
	private class FibonacciIterator implements Iterator<Integer> {
		private int n;
		
		public FibonacciIterator(int n) {
			this.n = n;
		}

		@Override
		public boolean hasNext() {
			return n > 0;
		}

		@Override
		public Integer next() {
			n--;
			return IterableFibonacci.this.fib.next();
		}
		
	}
	
	public static void main(String[] args) {
		for(Integer i : new IterableFibonacci(new Fibonacci(), 18)) {
			System.out.println(i);
		}
	}

}
