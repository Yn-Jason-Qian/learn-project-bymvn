package algorithm.section2;

public class Test002_4 {
	
	public static int gcd(int a, int b) {
		while(b != 0) {
			int rem = a % b;
			a = b;
			b = rem;
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(gcd(50, 15));
	}

}
