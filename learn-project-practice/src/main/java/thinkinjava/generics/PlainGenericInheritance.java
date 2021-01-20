package thinkinjava.generics;

class GenericSetter<T> {
	void set(T t) {}
}

class DerivedGenericSetter extends GenericSetter<Fruit> {
	void set(Fruit fruit) {}
	
	void set(Apple apple) {}
}

public class PlainGenericInheritance {

	public static void main(String[] args) {

	}

}
				