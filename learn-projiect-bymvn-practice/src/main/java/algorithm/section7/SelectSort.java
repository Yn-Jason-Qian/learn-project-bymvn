package algorithm.section7;

import algorithm.Util;

public class SelectSort {
	
	/**
	 * 选择排序
	 * 
	 * 以0作为当前位置开始，向后查找最小元素，然后交换最小元素与当前位置的元素。当前位置向后推移
	 * 
	 * 时间复杂度:O(N^2)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void selectSort(T[] arr) {
		for(int i=0; i < arr.length; i++) {
			int minIndex = i;
			for(int j = i + 1; j < arr.length; j++) {
				if(arr[minIndex].compareTo(arr[j]) > 0) {
					minIndex = j;
				}
			}
			Util.swapIndex(arr, i, minIndex);
		}
	}

}
