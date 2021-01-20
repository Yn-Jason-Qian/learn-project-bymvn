package algorithm.section7;

import algorithm.IntegerFactory;
import algorithm.ObjectFactory;

import java.util.Arrays;
import java.util.TreeSet;


public class SortTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
//		Integer[] arr = {34, 8, 64, 51, 32, 21};
		Comparable[] arr = initArr(1000000, new IntegerFactory());
		long start;
		start = System.currentTimeMillis();
//		InsertSort.insertSort2(arr);
//		BubbleSort.bubbleSort(arr);
//		SelectSort.selectSort(arr);
//		ShellSort.shellSort(arr);
//		HeapSort.heapSort(arr);
//		MergeSort.mergeSort(arr);
//		QuickSort.quickSort(arr);
		Arrays.sort(arr);
		System.out.println("time:" + (System.currentTimeMillis() - start));
	}
	
	/**
	 * 初始化数组，会有重复的数据
	 * 
	 * @param size
	 * @param factory
	 * @return
	 */
	public static <T extends Comparable<? super T>> T[] initArr(int size, ObjectFactory<T> factory) {
		@SuppressWarnings("unchecked")
		T[] arr = (T[]) new Comparable[size];
		for(int i = 0; i < size; i++) {
			arr[i] = factory.createObj();
		}
		return arr;
	}
	
	/**
	 * 初始化数组，没有重复的数据
	 * 
	 * @param size
	 * @param factory
	 * @return
	 */
	public static <T extends Comparable<? super T>> T[] initArr1(int size, ObjectFactory<T> factory) {
		@SuppressWarnings("unchecked")
		T[] arr = (T[]) new Comparable[size];
		TreeSet<T> set = new TreeSet<T>();
		for(int i = 0; i < size; i++) {
			T obj;
			while(set.contains(obj = factory.createObj())){}
			arr[i] = obj;
			set.add(obj);
		}
		return arr;
	}
	
}
