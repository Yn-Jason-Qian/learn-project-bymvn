package thinkinjava.generics;

/*
 * 自限定泛型
 */

class SelfBounded<T extends SelfBounded<T>> {
	T element;
	void set(T t) {this.element = t;}
	T get() {return this.element;}
}

class A extends SelfBounded<A> {}

class Asub extends A {}

class B extends SelfBounded<B> {}

class C extends SelfBounded<C> {
	C setAndGet(C arg) {
		set(arg);
		return get();
	}
} 

class D {}
//Bound mismatch: The type D is not a valid substitute for the bounded parameter <T extends SelfBounded<T>> of the type SelfBounded<T>
//class E extends SelfBounded<D> {}

//SelfBounded is a raw type. References to generic type SelfBounded<T> should be parameterized
@SuppressWarnings("rawtypes")
class F extends SelfBounded {}

public class SelfBounding {

	public static void main(String[] args) {
		A a = new A();
		a.set(new Asub());
	}

}
