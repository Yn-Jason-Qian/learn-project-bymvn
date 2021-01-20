package algorithm.interview;

import algorithm.Util;

public class Sorts {

    public static void insertSort(int[] arr, int start, int end) {
        if (arr == null || end > arr.length - 1 || start > arr.length - 1 || end < 0 || start < 0) {
            return;
        }
        int j;
        for (int i = start + 1; i <= end; i++) {
            int temp = arr[i];

            for (j = i - 1; j >= 0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
        }
    }

    public static void insertSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        insertSort(arr, 0 , arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1};
        insertSort(arr);
        Util.printIntArr(arr);
    }

}
