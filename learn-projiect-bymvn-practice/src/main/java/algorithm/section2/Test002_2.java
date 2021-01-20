package algorithm.section2;

public class Test002_2 {

	/**
	 * 最大子序列问题
	 */
	
	//O(n^3)算法
	static void method1(int[] arr) {
		int maxsum = 0;
		for(int i=0; i<arr.length; i++) {
			for(int j=i; j<arr.length; j++) {
				int thissum = 0;
				for(int y=i; y<=j; y++) {
					thissum += arr[y];
				}
				if(maxsum < thissum) {
					maxsum = thissum;
				}
			}
		}
		System.out.println("maxsum:" + maxsum);
	}
	
	//O(N^2)
	static void method2(int[] arr) {
		int maxsum = 0;
		for(int i=0; i<arr.length; i++) {
			int thissum = 0;
			for(int j=i; j<arr.length; j++) {
				thissum += arr[j];
				if(thissum > maxsum) {
					maxsum = thissum;
				}
			}
		}
		System.out.println("maxsum:" + maxsum);
	}
	
	//O(NlogN)
	static int method3(int[] arr, int left, int right) {
		if(left == right) {
			if(arr[left] < 0) {
				return 0;
			} else {
				return arr[left];
			}
		}
		int mid = (right + left) / 2;
		int leftsum = method3(arr, left, mid);
		int rightsum = method3(arr, mid + 1, right);
		
		int leftmaxsum = 0;
		int thissum = 0;
		for(int i=mid; i>=left; i--) {
			thissum += arr[i];
			if(leftmaxsum < thissum) {
				leftmaxsum = thissum;
			}
		}
		
		int rigthmaxsum = 0;
		int rigththissum = 0;
		for(int i=mid+1; i<=right; i++) {
			rigththissum += arr[i];
			if(rigthmaxsum < rigththissum) {
				rigthmaxsum = rigththissum;
			}
 		}
		return max3(leftsum, rightsum, leftmaxsum+rigthmaxsum);
	}
	
	private static int max3(int leftsum, int rightsum, int midsum) {
		int max = leftsum;
		if(max < rightsum) {
			max = rightsum;
		}
		if(max < midsum) {
			max = midsum;
		}
		return max;
	}
	
	//O(N)
	static void method4(int[] arr) {
		int maxsum = 0, thissum = 0;
		for(int i=0; i<arr.length; i++) {
			thissum += arr[i];
			if(thissum < 0) {
				thissum = 0;
			}
			if(thissum > maxsum) {
				maxsum = thissum;
			}
		}
		System.out.println("maxsum:" + maxsum);
	}

	public static void main(String[] args) {
		int[] arr = {4, -3, 5, -2, -1, 2, 6, -2};
		method1(arr);
		method2(arr);
		System.out.println(method3(arr, 0, arr.length - 1));
		System.out.println(max3(1 ,3, 2));
		method4(arr);
	}

}
