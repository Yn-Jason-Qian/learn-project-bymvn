package algorithm.interview;

import java.util.Arrays;

/**
 * Created by qianyang on 18-3-15.
 */
public class FindKLessKNumber {

    public static int[] find(int[] arr, int k) {
        int[] lengthOfK = new int[k];
        for (int i = 0; i < arr.length; i++) {
            if (i < k) {
                lengthOfK[i] = arr[i];
                HeapSort.siftUp(lengthOfK, i);
            } else {
                if (lengthOfK[0] > arr[i]) {
                    lengthOfK[0] = arr[i];
                    HeapSort.siftDown(lengthOfK, k, 0);
                }
            }
        }
        return lengthOfK;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(find(new int[]{1, 2, 3, 4, 5,7 ,6, 12, 10, 30,20,22}, 8)));
    }
}
