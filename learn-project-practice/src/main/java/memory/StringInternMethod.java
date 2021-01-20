package memory;

import java.lang.reflect.Field;

/**
 * 神奇的String.intern()方法
 * 
 * @author qianyang
 *
 */
public class StringInternMethod {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		stringConstantPool();
		reflectString();
	}
	
	static void stringConstantPool() {
		String a = new String("ab") + "c";
		System.out.println(a.intern() == a);
		System.out.println("abc" == a);
		
	}
	
	static void reflectString() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String a = "abc";
		
		Class<String> strClazz = String.class;
		Field field = strClazz.getDeclaredField("value");
		field.setAccessible(true);
		char[] value = (char[]) field.get(a);
		value[2] = 'd';
		
		System.out.println(a);
		System.out.println("abc" == a);
		System.out.println("abc");
		
		String b = new String("a") + "bc";
		String c = b.intern();
		System.out.println(c == b);
		System.out.println(c == "abc");
	}
	
	static void unUseInternMethod() {
		String a = "abc";
		String b = "def";
		String c = a + b;
		String c1 = c.intern();
		String d = "abcdef";
		System.out.println(c == c1);
		System.out.println(c1 == d);
		System.out.println(c == d);
	}

	static void useInternMethod() {
		String str1 = new StringBuilder("计算机").append("软件").toString();
		System.out.println(str1.intern() == str1);
		System.out.println("计算机软件" == str1);
		
		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
		System.out.println(str2 == "java");
	}
	
}
