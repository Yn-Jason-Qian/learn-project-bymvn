package algorithm.section7;

public class MergeSort {
	
	/**
	 * 归并排序
	 * 
	 * 合并两个数组的方法，利用递归
	 * 
	 * @param arr
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> void mergeSort(T[] arr) {
		T[] tmpArr = (T[]) new Comparable[arr.length];
//		merge(arr, tmpArr, 0, arr.length - 1);
		merge3(arr, tmpArr);
	}
	
	private static <T extends Comparable<? super T>> void merge(T[] arr, T[] tmpArr, int start, int end) {
		if(start >= end) {
			return;
		}
		int center = (start + end) / 2, i = start, j = center + 1;
		merge(arr, tmpArr, i, center);
		merge(arr, tmpArr, center + 1, end);
		for(int k = start; i <= center || j <= end;) {
			if(i > center) {
				tmpArr[k++] = arr[j++];
				continue;
			}
			if(j > end) {
				tmpArr[k++] = arr[i++];
				continue;
			}
			if(arr[i].compareTo(arr[j]) < 0) {
				tmpArr[k++] = arr[i++];
			} else{
				tmpArr[k++] = arr[j++];
			}
		}
		System.arraycopy(tmpArr, start, arr, start, (end - start + 1));
	}
	
	private static <T extends Comparable<? super T>> void merge2(T[] arr, T[] tmpArr, int start, int end) {
		int len = end - start + 1;
		if(len < 10) {
			InsertSort.insertSort(arr, start, end);
			return;
		}
		int center = (start + end) / 2;
		merge2(arr, tmpArr, start, center);
		merge2(arr, tmpArr, center + 1, end);
		int i = start, j = center + 1, k = start;
		for(; i <= center && j <= end;) {
			if(arr[i].compareTo(arr[j]) < 0) {
				tmpArr[k++] = arr[i++];
			} else {
				tmpArr[k++] = arr[j++];
			}
		}
		while(i <= center) {
			tmpArr[k++] = arr[i++];
		}
		while(j <= end) {
			tmpArr[k++] = arr[j++];
		}
		System.arraycopy(tmpArr, start, arr, start, len);
	}
	
	//尝试非递归归并排序
	private static <T extends Comparable<? super T>> void merge3(T[] arr, T[] tmpArr) {
		for(int k = 1; k < arr.length; k *= 2) {
			for(int n = 1; (2*n-1)*k <= arr.length; n++) {
				int i = (n-1)*2*k, j = 2*n*k - k, index = i;
				for(; i <= 2*n*k - k - 1 && j <= 2*n*k - 1 && j < arr.length; ) {
					if(arr[i].compareTo(arr[j]) < 0) {
						tmpArr[index++] = arr[i++];
					} else {
						tmpArr[index++] = arr[j++];
					}
				}
				while(i <= 2*n*k - k - 1) {
					tmpArr[index++] = arr[i++];
				}
				while(j <= 2*n*k - 1 && j < arr.length) {
					tmpArr[index++] = arr[j++];
				}
				System.arraycopy(tmpArr, (n-1)*2*k, arr, (n-1)*2*k, index - (n-1)*2*k);
			}
		}
	}
}
