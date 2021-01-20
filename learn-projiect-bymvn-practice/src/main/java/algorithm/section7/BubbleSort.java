package algorithm.section7;

import algorithm.Util;

public class BubbleSort{
	/**
	 * 冒泡排序
	 * 
	 * 每次循环比较两个相邻元素，如果逆序，则交换位置。每次循环都会获得最大的元素并且置于末尾
	 * 
	 * 时间复杂度：O(N^2)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void bubbleSort(T[] arr) {
		for(int i = arr.length - 1; i > 0; i--) {
			for(int j = 0; j < i; j++) {
				if(arr[j].compareTo(arr[j+1]) > 0) {
					Util.swapIndex(arr, j, j+1);
				}
			}
		}
	}

}
