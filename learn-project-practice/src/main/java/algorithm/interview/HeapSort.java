package algorithm.interview;

import java.util.Arrays;

/**
 * Created by qianyang on 18-3-15.
 */
public class HeapSort {

    public static void heapSort(int[] arr) {
        makeHeap(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            QuickSort.swap(arr, 0, i);
            siftDown(arr, i, 0);
        }
    }

    public static void siftDown(int[] arr, int length, int index) {
        if (arr == null || arr.length == 0 || index < 0 || index > arr.length - 1) {
            return;
        }
        while (index < length) {
            int swapIndex = index * 2 + 1;
            if (swapIndex > length - 1) {
                break;
            }
            if (swapIndex + 1 < length && arr[swapIndex + 1] > arr[swapIndex]) {
                swapIndex++;
            }
            if (arr[index] < arr[swapIndex]) {
                QuickSort.swap(arr, index, swapIndex);
                index = swapIndex;
            } else {
                break;
            }
        }
    }

    public static void siftUp(int[] arr, int index) {
        if (arr == null || arr.length == 0 || index < 0 || index > arr.length - 1) {
            return;
        }
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (arr[parent] < arr[index]) {
                QuickSort.swap(arr, parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    public static void makeHeap(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int index = (arr.length - 1) / 2;
        while (index >= 0) {
            siftDown(arr, arr.length, index);
            index--;
        }
    }

    public static int pop(int[] heap, int length) {
        int result = heap[0];
        QuickSort.swap(heap, 0, length - 1);
        siftDown(heap, length - 1, 0);
        return result;
    }



    public static void main(String[] args) {
        int arr[] = new int[]{1,3,4,5,6,7,8,2,11};
//        heapSort(arr);
        makeHeap(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(pop(arr, arr.length - i) + ",");
        }
        System.out.println(Arrays.toString(arr));
    }

}
