package effctiveJava.section5;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> {
	private Object[] objects;
	private int size;
	private final static int DEFAULT_INITIAL_CAPACITY = 16;
	
	public Stack() {
		objects = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	
	public void push(E element) {
		ensureCapacity();
		objects[size++] = element;
	}
	
	public E pop() {
		if(size == 0) {
			throw new EmptyStackException();
		}
		@SuppressWarnings("unchecked")
		E element = (E) objects[--size];
		objects[size] = null;
		return element;
	}
	
	private void ensureCapacity() {
		if(size == objects.length) {
			objects = Arrays.copyOf(objects, 2 * size + 1);
		}
	}
}
