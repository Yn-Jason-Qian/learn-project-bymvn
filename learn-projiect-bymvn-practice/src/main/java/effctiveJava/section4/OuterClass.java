package effctiveJava.section4;

import java.util.Comparator;

public class OuterClass {

	private static int staticField;
	
	private int nonStaticField;
	
	private static void staticMethod() {
		System.out.println("invoke static method");
	}
	
	private void nonStaticMethod() {
		System.out.println("invoke non static method");
	}
	
	static class StaticInnerClass {
		
		public void doing() {
			staticMethod();
			System.out.println(staticField);
		}
		
	}
	
	class NonStaticInnerClass {
		
		public void doing() {
			nonStaticMethod();
			System.out.println(nonStaticField);
		}
		
	}
	
	public void anonymousClass() {
		int a = 0;
		new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				System.out.println(nonStaticField);
				System.out.println(OuterClass.this);
				System.out.println(staticField);
				System.out.println(a);
				return 0;
			}
		};
	}
	
	public void localClass() {
		class LocalClass{
			
		}
	}
	
	static Comparator<String> comp = new Comparator<String>() {
		
		int a;
		
		@Override
		public int compare(String o1, String o2) {
//			System.out.println(nonStaticField);
//			System.out.println(OuterClass.this);
			System.out.println(a);
			return 0;
		}
	};
	
	public static void main(String[] args) {
		comp.compare(null, null);
	}
	
	
}
