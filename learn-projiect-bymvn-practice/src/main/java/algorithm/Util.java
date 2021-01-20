package algorithm;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public final class Util {

	private static Random random = new Random(37);
	
	public static int nextInt(int n) {
		return random.nextInt(n);
	}
	
	public static int nextPostiveInt(int n) {
		return random.nextInt(n) + 1;
	}
	
	public static int nextInt() {
		return random.nextInt();
	}
	
	public final static <T> void swapIndex(T[] arr, int i, int j) {
		T tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	public static void main(String[] args) {
		System.out.println(new Date(1480435200000L));
	}
	
	public static <T> void printArr(T[] arr) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < arr.length; i++) {
			builder.append(arr[i].toString() + ",");
		}
		System.out.println(builder.substring(0, builder.length() - 1));
	}

	public static void printIntArr(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	public static void print(Node head) {
		Node cur = head;
		while (cur != null) {
			System.out.println(cur.value);
			cur = cur.next;
		}
	}

	public static boolean isOdd(int number) {
		return number - 1 == (number & (number - 1));
	}
}
