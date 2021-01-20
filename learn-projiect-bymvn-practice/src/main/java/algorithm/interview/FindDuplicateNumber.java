package algorithm.interview;

import java.util.Arrays;

/**
 * Created by qianyang on 18-3-15.
 */
public class FindDuplicateNumber {

    public static int find(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Arr is empty.");
        }
        int number = quickSortFind(arr, 0, arr.length - 1, arr.length / 2);
        if (!checkMoreThanHalf(arr, number)) {
            return -1;
        }
        return number;
    }

    public static int findByCount(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Arr is empty.");
        }
        int number = arr[0];
        int count = 1;
        for (int i = 1; i < arr.length; i++) {
            if (count == 0) {
                number = arr[i];
                count = 1;
            } else if (number == arr[i]){
                count++;
            } else {
                count--;
            }
        }
        if (!checkMoreThanHalf(arr, number)) {
            return -1;
        }
        return number;
    }

    private static boolean checkMoreThanHalf(int[] arr, int number) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == number) {
                count++;
            }
        }
        return count > (arr.length / 2);
    }

    public static int findk(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Arr is empty.");
        }
        return quickSortFind(arr, 0 , arr.length - 1, k - 1);
    }

    public static int quickSortFind(int[] arr, int start, int end, int index) {
        if (start < 0 || start > arr.length - 1 ||
                end < 0 || end > arr.length - 1 ||
                start > index || end < index) {
            return -1;
        }
        if (end - start < 5) {
            Sorts.insertSort(arr, start, end);
            return arr[index];
        }
        int middle = (start + end) / 2;
        if (arr[start] > arr[middle]) {
            QuickSort.swap(arr, start, middle);
        }
        if (arr[middle] > arr[end]) {
            QuickSort.swap(arr, middle, end);
        }
        if (arr[start] > arr[middle]) {
            QuickSort.swap(arr, start, middle);
        }
        QuickSort.swap(arr, middle, end - 1);
        int startPoint = start;
        int endPoint = end - 1;
        while (startPoint < endPoint) {
            while (arr[++startPoint] < arr[end - 1]) {}
            while (arr[--endPoint] > arr[end - 1]) {}
            if (startPoint < endPoint) {
                QuickSort.swap(arr, startPoint, endPoint);
            }
        }
        QuickSort.swap(arr, startPoint, end - 1);
        if (startPoint == index) {
            return arr[index];
        } else if (startPoint < index) {
            return quickSortFind(arr, startPoint + 1, end, index);
        } else {
            return quickSortFind(arr, start, startPoint - 1, index);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 2, 4, 2, 5, 2, 2, 7, 10, 11, 12, 13, 14, 2};
        System.out.println(find(arr));
        System.out.println(findk(arr, 16));
        System.out.println(arr.length);
        System.out.println(Arrays.toString(arr));
    }

}
