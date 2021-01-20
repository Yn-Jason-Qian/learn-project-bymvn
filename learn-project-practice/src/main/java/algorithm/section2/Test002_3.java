package algorithm.section2;

public class Test002_3 {

	static int binarySearch(int[] arr, int value) {
		int start = 0, end = arr.length - 1, index;
		while(start <= end) {
			index = (start + end) / 2;
			if(arr[index] == value) {
				return index;
			} else if(arr[index] > value) {
				end = index - 1;
			} else {
				start = index + 1;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] arr = {1, 2, 4, 6, 7};
		System.out.println(binarySearch(arr, 4));
	}

}
