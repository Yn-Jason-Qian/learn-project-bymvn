package net.stealthcat.test.clazz;

public class CompileTest {

	interface InterfaceA {
		int A = 1;
	}
	
	interface InterfaceB extends InterfaceA{
		int A = 2;
	}
	
	interface InterfaceC {
		int A = 3;
	}
	
	static class Pub implements InterfaceB {
		public static final int A = 4;
	}
	
	static class Sub extends Pub implements InterfaceC {
		
	}
	
	public static void main(String[] args) {
		
		//compile error : The field Sub.A is ambiguous
//		System.out.println(Sub.A);

	}

}
