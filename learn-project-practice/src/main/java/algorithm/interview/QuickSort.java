package algorithm.interview;

import algorithm.Util;

public class QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int start, int end) {
        if (end - start < 5) {
            Sorts.insertSort(arr, start, end);
            return;
        }
        int middle = start + end / 2;

        if (arr[start] > arr[middle]) {
            swap(arr, start, middle);
        }
        if (arr[middle] > arr[end]) {
            swap(arr, middle, end);
        }
        if (arr[start] > arr[middle]) {
            swap(arr, start, middle);
        }
        swap(arr, middle, end - 1);

        int startPoint = start;
        int endPoint = end - 1;
        while (startPoint < endPoint) {
            while(arr[++startPoint] < arr[end - 1] && startPoint < end - 1) {}
            while (arr[--endPoint] > arr[end - 1] && endPoint > start) {}
            if (startPoint < endPoint) {
                swap(arr, startPoint, endPoint);
            }
        }
        swap(arr, startPoint, end - 1);
        quickSort(arr, start, middle - 1);
        quickSort(arr, middle + 1, end);
    }

    public static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    public static void main(String[] args) {
        int arr[] = new int[]{3, 2, 1, 4, 9, 10, 20, 30, 11, 41, 51, 41};
        quickSort(arr);
        Util.printIntArr(arr);
    }

}
