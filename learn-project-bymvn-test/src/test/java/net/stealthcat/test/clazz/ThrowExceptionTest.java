package net.stealthcat.test.clazz;

public class ThrowExceptionTest {

	public static void main(String[] args) {
		try {
			System.out.println(2.0/-0.0);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
