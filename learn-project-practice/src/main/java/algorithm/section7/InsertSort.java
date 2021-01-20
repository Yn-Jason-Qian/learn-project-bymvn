package algorithm.section7;

import algorithm.Util;

public class InsertSort {

	/**
	 * 插入排序
	 * 
	 * 从大于0的初始位置开始，设初始位置前的都是已排序的元素，将当前位置元素插入到前面排序的元素中
	 * 
	 * 时间复杂度:O(N^2)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void insertSort(T[] arr) {
		for(int i=0; i < arr.length - 1; i++) {
			T tmp = arr[i + 1];
			for(int j=i; j >= 0; j--) {
				if(arr[j].compareTo(tmp) > 0) {
					arr[j+1] = arr[j];
				} else {
					arr[j+1] = tmp;
					break;
				}
				if(j == 0) {
					arr[j] = tmp;
				}
			}
			
		}
	}
	
	public static <T extends Comparable<? super T>> void insertSort2(T[] arr) {
		for(int i = 1; i < arr.length; i++) {
			int j;
			T tmp = arr[i];
			for(j = i; j > 0 && tmp.compareTo(arr[j - 1]) < 0; j--) {
				arr[j] = arr[j - 1];
			}
			arr[j] = tmp;
		}
	}
	
	public static <T extends Comparable<? super T>> void insertSort(T[] arr, int start, int end) {
		int len = end - start + 1;
		for(int i = start + 1; i < len; i++) {
			for(int j = i; j > start && arr[j].compareTo(arr[j - 1]) < 0; j--) {
				Util.swapIndex(arr, j, j - 1);
			}
		}
	}
	
}
