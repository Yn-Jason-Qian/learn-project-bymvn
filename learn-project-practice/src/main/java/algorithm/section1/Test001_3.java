package algorithm.section1;

import java.math.BigDecimal;

public class Test001_3 {

	static void printDigit(int n) {
		System.out.print(n);
	}
	
	static void printDouble(BigDecimal d) {
	}
	
	public static void main(String[] args) {
		Double d = 1.1;
		BigDecimal num = new BigDecimal(d);
		printDouble(num);
	}

}
