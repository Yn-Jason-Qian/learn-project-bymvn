package algorithm.section6;

import algorithm.Util;
import algorithm.section7.HeapSort;
import algorithm.section7.MergeSort;
import algorithm.section7.QuickSort;

/**
 * 求第ｋ个最大元素
 * 
 * @author qianyang
 *
 */
public class GetKthLargeValueByBinaryHeap {

	static int len = 1000000;
	
	static int k = len / 2;
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[len];
		int[] arr1 = new int[len];
		for(int i=0; i<len; i++) {
			int v = Util.nextInt();
			arr[i] = v;
			arr1[i] = v;
		}
//		Test001_1.test(arr1);
		int kth; long start;
		start = System.currentTimeMillis();
		kth = selectKthByDeleteMin(arr);
		System.out.println("kth = : " + kth + ", time : " + (System.currentTimeMillis() - start));
		
		start = System.currentTimeMillis();
		kth = selectKthByInsert(arr);
		System.out.println("kth = : " + kth + ", time : " + (System.currentTimeMillis() - start));
		
		//快速选择是查询第Ｋ个最小的元
		start = System.currentTimeMillis();
		kth = QuickSort.quickSelect(arr, k);
		System.out.println("kth = : " + kth + ", time : " + (System.currentTimeMillis() - start));
		
		//归并排序
		Integer[] copy = new Integer[len];
		System.arraycopy(arr, 0, copy, 0, len);
		start = System.currentTimeMillis();
		MergeSort.mergeSort(copy);
		kth = copy[len - k];
		System.out.println("kth = : " + kth + ", time : " + (System.currentTimeMillis() - start));
		
		//堆排序
		Integer[] copy２ = new Integer[len];
		System.arraycopy(arr, 0, copy２, 0, len);
		start = System.currentTimeMillis();
		HeapSort.heapSort(copy２);
		kth = copy２[len - k];
		System.out.println("kth = : " + kth + ", time : " + (System.currentTimeMillis() - start));
	}
	
	public static int selectKthByDeleteMin(Integer[] arr) {
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>(arr);
		int kth = 0;
		for(int i=0; i < len - k + 1; i++) {
			kth = heap.deleteMin();
		}
		return kth;
	}
	
	public static int selectKthByInsert(Integer[] arr) {
		BinaryHeap<Integer> heap = new BinaryHeap<Integer>(k);
		for(int i=0; i < k; i++) {
			heap.insert(arr[i]);
		}
		for(int i=k; i < len; i++) {
			if(heap.findMin() < arr[i]) {
//				heap.deleteMin();
//				heap.insert(arr[i]);
				heap.set(1, arr[i]);
				heap.percolateDown(1);
			}
		}
		return heap.findMin();
	}

}
