package net.stealthcat.leecode;

import java.util.Arrays;

public class ReverseArr {

    public static void main(String[] args) {
        String[] arr = {"1", "2", "3", "4"};
        System.out.println(Arrays.toString(reverse(arr)));
    }

    private static String[] reverse(String[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        int halfLen = arr.length / 2;
        for (int i = 0; i < halfLen; i++) {
            String temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
        return arr;
    }

    private static String[] reverse1(String[] arr) {
        return null;
    }

}
