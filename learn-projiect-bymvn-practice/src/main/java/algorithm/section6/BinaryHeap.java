package algorithm.section6;

import algorithm.Exception.OverFlowException;

public class BinaryHeap<T extends Comparable<? super T>> {

	private static final int DEFAULT_CAPACITY = 10;
	
	private int currentSize;
	
	private T[] array;
	
	public BinaryHeap () {
		this(DEFAULT_CAPACITY);
	}
	
	@SuppressWarnings("unchecked")
	public BinaryHeap (int capacity) {
		array = (T[]) new Comparable[capacity + 1];
		currentSize = 0;
	}
	
	@SuppressWarnings("unchecked")
	public BinaryHeap (T[] items) {
		array = (T[]) new Comparable[(items.length + 2) * 11 / 10];
		System.arraycopy(items, 0, array, 1, items.length);
		currentSize = items.length;
		buildHeap();
	}
	
	public void insert(T x) {
		if(isFull()) {
			throw new OverFlowException();
		}
		int hole = ++currentSize;
		for(; hole>1 && array[hole/2].compareTo(x) > 0; hole /= 2) {
			array[hole] = array[hole/2];
		}
		array[hole] = x;
	}
	
	public T findMin() {
		if(isEmpty()) {
			return null;
		}
		return array[1];
	}
	
	public void set(int index, T v) {
		array[index] = v;
	}
	
	public T deleteMin() {
		T minItem = findMin();
		array[1] = array[currentSize];
		percolateDown(1);
		currentSize--;
		return minItem;
	}
	
	public boolean isFull() {
		return currentSize == array.length - 1;
	}
	
	public boolean isEmpty () {
		return currentSize == 0;
	}
	
	public void makeEmpty () {
		currentSize = 0;
	}
	
	public void percolateDown (int hole) {
		int child;
		T tmp = array[hole];
		for(; hole*2 <= currentSize; hole = child) {
			child = hole * 2;
			if(child != currentSize && array[child].compareTo(array[child + 1]) > 0) {
				child++;
			}
			if(array[child].compareTo(tmp) < 0) {
				array[hole] = array[child];
			} else {
				break;
			}
		}
		array[hole] = tmp;
	}
	
	private void buildHeap () {
		for(int hole = currentSize / 2; hole > 0; hole--) {
			percolateDown(hole);
		}
	}
	
}
