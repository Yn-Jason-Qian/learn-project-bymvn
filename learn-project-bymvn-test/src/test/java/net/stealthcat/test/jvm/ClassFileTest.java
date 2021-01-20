package net.stealthcat.test.jvm;

public class ClassFileTest {
	
	public static void main(String[] args) {
		System.out.println(inc());
	}

//	private int y = 2;
	
	public static int inc() {
		int x;
		try {
			x = 1;
//			throw new RuntimeException();
			return x;
		} catch (Exception e) {
			x = 2;
			return x;
		} finally {
			x = 3;
//			return x;
		}
	}
	
	class a {
		
	}
	
//	public static void main(String[] args) {
//		Integer a = 1;
//		Integer b = 2;
//		Integer c = 3;
//		Integer d = 3;
//		Integer e = 321;
//		Integer f = 321;
//		Long g = 3L;
//		System.out.println(c == d);
//		System.out.println(e == f);
//		System.out.println(c == (a+b));
//		System.out.println(c.equals(a+b));
//		System.out.println(g == (a+b));
//		System.out.println(g.equals(a+b));
//		System.out.println(3L == 3);
//	}
	
}
