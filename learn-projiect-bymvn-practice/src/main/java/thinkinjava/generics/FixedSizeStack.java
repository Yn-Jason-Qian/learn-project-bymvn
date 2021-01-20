package thinkinjava.generics;

public class FixedSizeStack<T> {

	private int index;
	
	private Object[] storage;
	
	public FixedSizeStack(int size) {
		storage = new Object[size];
	}
	
	public void push(T t) {
		storage[index++] = t;
	}
	
	@SuppressWarnings("unchecked")
	public T pop() {
		return (T) storage[index--];
	}
	
}
