package algorithm.section7;

import algorithm.Util;

public class QuickSort {

	/* 截止范围 */
	private final static int CUTOFF_RANGE = 10;
	
	/**
	 * 快速排序
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void quickSort(T[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}
	
	private static <T extends Comparable<? super T>> void quickSort(T[] arr, int start, int end) {
		int len = end - start + 1;
		if(len < 2) {
			return;
		}
		if(len < CUTOFF_RANGE) {
			insertSort(arr, start, end);
			return;
		}
		int center = (start + end) / 2;
		if(arr[start].compareTo(arr[center]) > 0) {
			Util.swapIndex(arr, start, center);
		}
		if(arr[end].compareTo(arr[start]) < 0) {
			Util.swapIndex(arr, end, start);
		}
		if(arr[center].compareTo(arr[end]) > 0) {
			Util.swapIndex(arr, center, end);
		}
		
		Util.swapIndex(arr, center, end - 1);
		//此处注意,1.因为2处原因，所以i,j的开始位置稍稍提前，实际任然是i = start + 1, j = end - 2
		int i = start, j = end - 1;
		for(;;) {
			//2.每次循环开始都要对i,j进行位移，不能写在大括号内，如果arr[i] = arr[j] = arr[end - 1]，会导致死循环
			while(arr[++i].compareTo(arr[end - 1]) < 0) {}
			while(arr[--j].compareTo(arr[end - 1]) > 0) {}
			if(i < j) {
				Util.swapIndex(arr, i, j);
			} else {
				break;
			}
		}
		Util.swapIndex(arr, i, end - 1);
		quickSort(arr, start, i - 1);
		quickSort(arr, i + 1, end);
	}
	
	public static <T extends Comparable<? super T>> void insertSort(T[] arr, int start, int end) {
		int len = end - start + 1;
		for(int i = start + 1; i < len; i++) {
			for(int j = i; j > start && arr[j].compareTo(arr[j - 1]) < 0; j--) {
				Util.swapIndex(arr, j, j - 1);
			}
		}
	}
	
	/**
	 * 求第Ｋ个最小元素
	 * 
	 * @param arr
	 * @param k
	 * @return
	 */
	public static <T extends Comparable<? super T>> T quickSelect(T[] arr, int k) {
		quickSelect(arr, 0, arr.length - 1, k);
		return arr[k - 1];
	}
	
	private static <T extends Comparable<? super T>> void quickSelect(T[] arr, int left, int right, int k) {
		int len = right - left + 1;
		if(len < 2) {
			return;
		}
		if(len < CUTOFF_RANGE) {
			insertSort(arr, left, right);
			return;
		}
		int center = (right + left) / 2;
		//此处与上面排序的比较略有不同
		if(arr[left].compareTo(arr[center]) > 0) {
			Util.swapIndex(arr, left, center);
		}
		if(arr[center].compareTo(arr[right]) > 0) {
			Util.swapIndex(arr, center, right);
			if(arr[center].compareTo(arr[left]) < 0) {
				Util.swapIndex(arr, center, left);
			}
		}
		Util.swapIndex(arr, center, right - 1);
		int i = left, j = right - 1;
		for(;;) {
			while(arr[++i].compareTo(arr[right - 1]) < 0) {}
			while(arr[--j].compareTo(arr[right - 1]) > 0) {}
			if(i < j) {
				Util.swapIndex(arr, i, j);
			} else {
				break;
			}
		}
		Util.swapIndex(arr, i, right - 1);
		if(i < (k - 1)) {
			quickSelect(arr, i + 1, right, k);
		} else if(i > (k - 1)) {
			quickSelect(arr, left, i - 1, k);
		}
	}
	
}
