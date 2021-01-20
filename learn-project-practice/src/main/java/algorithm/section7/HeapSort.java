package algorithm.section7;

public class HeapSort {

	/**
	 * 堆排序
	 * 
	 * 利用堆的性质，buildHeap需要O(N),N次deleteMax,每次需要O(logN),所以时间复杂度为O(NlogN)
	 * 
	 * @param arr
	 */
	public static <T extends Comparable<? super T>> void heapSort(T[] arr) {
		MaxHeap<T> heap = new MaxHeap<T>(arr);
		for(int i = 0; i < arr.length; i++) {
			T max = heap.deleteMax();
			arr[arr.length - i - 1] = max;
		}
		
	}
	
	private static class MaxHeap<T extends Comparable<? super T>> {
		T[] arr;
		int currentSize;
		MaxHeap (T[] arr) {
			currentSize = arr.length;
			this.arr  = arr;
			buildHeap();
		}
		
		T deleteMax() {
			T max = arr[0];
			arr[0] = arr[currentSize-- - 1];
			siftDown(0);
			return max;
		}
		
		void buildHeap() {
			for(int i = (currentSize - 2) / 2; i >= 0; i--) {
				siftDown(i);
			}
		}
		
		void siftDown(int i) {
			T tmp = arr[i];
			int child;
			for(child = 2 * i + 1; child <= currentSize - 1; child = child * 2 + 1) {
				if(child != currentSize - 1 && arr[child].compareTo(arr[child + 1]) < 0) {
					child++;
				}   
				if(tmp.compareTo(arr[child]) < 0) {
					arr[i] = arr[child];
					i = child;
				} else {
					break;
				}
			}
			arr[i] = tmp;
//			for(; ;)
		}
	}
}
