package algorithm.section7;

public class ShellSort {

	/**
	 * 希尔排序
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void shellSort(T[] arr) {
		for(int gap = arr.length / 2; gap > 0; gap /= 2) {
			for(int i = gap; i < arr.length; i++) {
				int j;
				T tmp = arr[i];
				for(j = i; j >= gap && tmp.compareTo(arr[j - gap]) < 0; j -= gap) {
					arr[j] = arr[j - gap];
				}
				arr[j] = tmp;
			}
		}
	}
	
}
