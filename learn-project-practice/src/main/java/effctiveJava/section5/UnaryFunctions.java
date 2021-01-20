package effctiveJava.section5;

public class UnaryFunctions {

	private static UnaryFunction<Object> IDENTITY_FUNCTION = new UnaryFunction<Object>() {
		
		@Override
		public Object apply(Object arg) {
			return arg;
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <T> UnaryFunction<T> identityFunction() {
		return (UnaryFunction<T>) IDENTITY_FUNCTION;
	}
	
	public static void main(String[] args) {
		String[] strs = {"a", "b", "c"};
		UnaryFunction<String> sameString = identityFunction();
		for(String str : strs) {
			System.out.println(sameString.apply(str));
		}
		
		Integer[] ints = {1, 2, 3};
		UnaryFunction<Integer> sameInt = identityFunction();
		for(Integer i : ints) {
			System.out.println(sameInt.apply(i));
		}
		System.out.println(sameString);
		System.out.println(sameInt);
	}
	
}
