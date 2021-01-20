package net.stealthcat.test.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ReflectModifier {

	public static void main(String[] args) {
		testConstructorModifiers(TestClass.class);
		testFieldModifiers(TestClass.class, "a");
		testMethodModifiers(TestClass.class, "method");
	}
	
	static void testClassModifier(Class<?> clazz) {
		print(TestClass.class.getModifiers());
	}
	
	static void testInterfaceModifiers(Class<?> clazz) {
		print(TestClass.class.getModifiers());
	}
	
	static void testConstructorModifiers(Class<?> clazz) {
		try {
			Constructor<?> c = clazz.getDeclaredConstructor(null);
			print(c.getModifiers());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	static void testMethodModifiers(Class<?> clazz, String methodName) {
		try {
			print(clazz.getDeclaredMethod(methodName, null).getModifiers());
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	static void testFieldModifiers(Class<?> clazz, String fieldName) {
		try {
			print(clazz.getDeclaredField(fieldName).getModifiers());
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	static void print(int modifiers) {
		System.out.println(Modifier.toString(modifiers));
	}
	
	public static class TestClass {
		
		private TestClass() {
			
		}
		
		private static volatile transient String a = "abc";
		
		private strictfp void method() {
			
		}
	}

}
