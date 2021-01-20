package effctiveJava.section7;

import java.util.Arrays;

public class ArraysAsListMethod {

	public static void main(String[] args) {
		int[] iArr = {1, 2, 3};
		String[] sArr = {"abc", "def", "gh"};
		System.out.println(Arrays.asList(iArr));
		System.out.println(Arrays.asList(sArr));
	}

}
