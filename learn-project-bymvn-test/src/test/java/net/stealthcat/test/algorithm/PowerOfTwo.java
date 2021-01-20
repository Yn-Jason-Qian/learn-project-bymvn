package net.stealthcat.test.algorithm;


/**
 * 判断是否是2的n次方，以及循环遍历的算法 
 */
public class PowerOfTwo {

	public static void main(String[] args) throws InterruptedException {
		
		for(int i = 0; i < 30; i++) {
			System.out.println(num);
			next();
			next0();
			num++;
			Thread.sleep(100);
		}
	}
	
	//判断是否是2^n
	private static boolean isPowerOfTwo(int val) {
		System.out.println(-val);
		System.out.println(val & -val);
        return (val & -val) == val;
    }

	private static int length = 8;
	
	private static int num = Integer.MAX_VALUE;
	
	/**
	 * 
	 */
	private static void next() {
		System.out.print("next:");
		System.out.println(num & length - 1);
	}
	
	private static void next0() {
		System.out.print("next0:");
		System.out.println(Math.abs(num % length));
	}
}
