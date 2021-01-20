package algorithm.section1;

import java.util.Random;

public class Test001_1 {

	static int selectK(int[] arr) {
		int[] tmp = sort(arr);
//		printArr(arr);
		return tmp[tmp.length/2 - 1];
	}
	
	static void printArr(int[] arr) {
		for(int i : arr) {
			System.out.print(i + ",");
		}
		System.out.println();
	}
	
	static int[] sort(int[] arr) {
		int[] arrTmp = new int[arr.length];
		System.arraycopy(arr, 0, arrTmp, 0, arr.length);
		for(int x=0;x<arrTmp.length;x++) {
			for(int y=x;y<arrTmp.length;y++) {
				if(arrTmp[x] < arrTmp[y]) {
					int tmp = arrTmp[x];
					arrTmp[x] = arrTmp[y];
					arrTmp[y] = tmp;
				}
			}
		}
		return arrTmp;
	}
	
	static Random random = new Random(37);
	public static void main(String[] args) {
		int[] arr = new int[100000];
		for(int i=0;i<arr.length;i++) {
			arr[i] = random.nextInt();
		}
		test(arr);
	}
	
	public static void test(int[] arr) {
		long start = System.currentTimeMillis();
		System.out.println(selectK(arr));
		System.out.println("time : " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		System.out.println(selectk2(arr));
		System.out.println("time : " + (System.currentTimeMillis() - start));
	}
	
	static int selectk2(int[] arr) {
		int len = arr.length % 2 == 0 ? arr.length/2 : (arr.length/2 + 1);
		int[] tmp0 = new int[len];
		System.arraycopy(arr, 0, tmp0, 0, tmp0.length);
		int[] tmp = sort(tmp0);
		for(int i=tmp.length;i<arr.length;i++) {
			if(arr[i] <= tmp[len - 1]) {
				continue;
			} else {
				int index = find(tmp, arr[i]);
				System.arraycopy(tmp, index, tmp, index + 1, tmp.length - index - 1);
				tmp[index] = arr[i];
			}
		}
		return tmp[tmp.length - 1];
	}
	
	static int find(int[] arr, int value) {
//		System.out.println(value);
		if(arr[0] < value) {
			return 0;
		}
		int start = 0, end = arr.length - 1;
		while(start <= end) {
			int len = end - start + 1;
			int addIndex = len % 2 == 0 ? len / 2 : (len / 2 + 1);
			int i = start + addIndex - 1;
			if(arr[i] == value) {
				return i;
			} else if(arr[i] < value){
				end = i - 1;
			} else {
				start = i + 1;
			}
		}
		if(start == end) {
			return start;
		} else if(start > arr.length - 1){
			return end;
		} else {
			return start;
		}
	}

}
