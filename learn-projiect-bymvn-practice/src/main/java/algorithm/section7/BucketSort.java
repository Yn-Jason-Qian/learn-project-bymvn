package algorithm.section7;

import algorithm.Util;

public class BucketSort {

	/**
	 * 桶式排序
	 * 
	 * 需限定arr的元素都小于ｍ，即元素的范围要符合数据下标的规范，大于等于０，小于m
	 * @param arr
	 */
	@SuppressWarnings("unchecked")
	public static void bucketSort(Integer[] arr, int m) {
		int[] tmp = new int[m];
		for(int i = 0; i < arr.length; i++) {
			tmp[arr[i] - 1] += 1;
		}
		for(int i = 0, j = 0; j < tmp.length; j++) {
			if(tmp[j] > 0) {
				arr[i++] = j + 1;
			}
		}
	}
	
	public static void main(String[] args) {
		Integer[] arr = {10,9,11,2,5,7,6,8,3,1};
		bucketSort(arr, 11);
		Util.printArr(arr);
	}

}
