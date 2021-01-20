package net.stealthcat.test.clazz;

public class CompileInitialTest {

	private static int a = 1;
	static {
		a = 2;
		b = 4;
	}
	static int b;
	
	public static void main(String[] args) {
		System.out.println(b);

	}

}
