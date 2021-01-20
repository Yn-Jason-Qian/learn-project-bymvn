package net.stealthcat.util;

import java.util.Scanner;

public class CommonUtil {
	private CommonUtil(){}
	
	public static boolean isPowerOfTwo(int val) {
	     return (val & -val) == val;
	}

	public static void handleInput(InputHandler handler) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			handler.handle(scanner.nextLine());
		}
	}

	public interface InputHandler {

		void handle(String input);

	}

}
