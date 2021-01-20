package thinkinjava.generics;

public class GenericTest {

	public static <T> void f1(Generic1<? super T> g1, T t) {
		g1.set(t);
	}
	
	public static <T> void f2(Generic2<? extends T> g2) {
		g2.get();
	}
	
	public static void main(String[] args) {
		f1(new Generic1<Object>(), new Object());
		f2(new Generic2<Object>());
	}
	
}


class Generic1<T> {
	void set(T t) {
	}
}

class Generic2<T> {
	T get() {
		return null;
	}
}

