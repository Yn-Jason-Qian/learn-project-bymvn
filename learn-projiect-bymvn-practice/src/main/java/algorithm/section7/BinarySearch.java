package algorithm.section7;

/**
 * Created by qianyang on 18-3-6.
 */
public class BinarySearch {

    public static void main(String[] args) {
        System.out.println(search(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 5));
    }

    public static int search(int[] arr, int value) {
        if (arr == null || arr.length <= 0 || value < arr[0] || value > arr[arr.length - 1]) {
            return -1;
        }
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int center = (start + end) / 2;
            if (arr[center] > value) {
                end = center - 1;
            } else if (arr[center] < value) {
                start = center + 1;
            } else {
                return center;
            }
        }
        return -1;
    }
}
