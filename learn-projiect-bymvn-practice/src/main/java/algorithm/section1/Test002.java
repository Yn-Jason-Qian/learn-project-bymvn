package algorithm.section1;


public class Test002 {
	
	static void printDigit(int n) {
		System.out.print(n);
	}
	
	static void printOut(int n) {
		if(n >= 10) {
			printOut(n/10);
		}
		printDigit(n%10);
	}

	public static void main(String[] args) {
		printOut(76234);
	}

}
