package algorithm.section1;

public class Test001 {

	/**
	 * f(x) = 2f(x-1) + x^2
	 * f(0) = 0;
	 */
	
	public static int sub(int x) {
		if(x == 0) {
			return 0;
		}
		return 2*sub(x - 1) + x*x;
	}
	
	public static void main(String[] args) {
		System.out.println(sub(1));
		System.out.println(sub(2));
	}

}
