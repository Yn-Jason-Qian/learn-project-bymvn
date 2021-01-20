package effctiveJava.section5;

import java.util.HashSet;
import java.util.Set;

public class Unlimitedwildcard {

	public static void main(String[] args) {
		Set<?> test = new HashSet<String>();
		
	}
	
	static int numElementsInCommon(Set<?> s1, Set<?> s2) {
		int result = 0;
		for(Object obj : s1) {
			if(s2.contains(obj)) {
				result++;
			}
		}
		return result;
	}
	
}
