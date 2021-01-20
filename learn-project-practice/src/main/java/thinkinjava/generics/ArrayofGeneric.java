package thinkinjava.generics;

public class ArrayofGeneric {

	static final int size = 100;
	static Generic<Integer>[] gia;
	
	
	public static void main(String[] args) {
		//[Ljava.lang.Object; cannot be cast to [Lcom.thinkinjava.generics.Generic;
//		gia = (Generic[]) new Object[size];
//		Object[] a = new Integer[10];
//		Integer[] b = (Integer[]) a;
		Generic<Integer> gen = new Generic<Integer>(10);
		Integer[] intArr = gen.rep();
	}
}

class Generic<T> {
	T[] t;
	@SuppressWarnings("unchecked")
	Generic(int size) {
		t = (T[]) new Object[size];
	}
	
	T[] rep() {
		return t;
	}
}
