package effctiveJava.section8;

public class CompareStringAndStringBuilderPerform {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String result = "";
		for(int i=0;i<100000;i++) {
			result = result + i + "\n";
		}
		System.out.println(System.currentTimeMillis() - start);
		
		start = System.currentTimeMillis();
		StringBuilder builder = new StringBuilder(100 * 5);
		for(int i=0;i<100000;i++) {
			builder.append(i + "\n");
		}
		System.out.println(System.currentTimeMillis() - start);
	}

}
