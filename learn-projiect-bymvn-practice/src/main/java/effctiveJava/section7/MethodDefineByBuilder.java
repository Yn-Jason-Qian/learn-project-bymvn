package effctiveJava.section7;

public class MethodDefineByBuilder {
	
	public void method(int arg1, int arg2, String arg3) {
		System.out.println(arg1 + arg2 + arg3);
	}
	
	public void method(Builder builder) {
		System.out.println(builder.arg1 + builder.arg2 + builder.arg3);
	}
	
	public static class Builder{
		private int arg1;
		private int arg2;
		private String arg3;
		public Builder setArg1(int arg1) {
			this.arg1 = arg1;
			return this;
		}
		public Builder setArg2(int arg2) {
			this.arg2 = arg2;
			return this;
		}
		public Builder setArg3(String arg3) {
			this.arg3 = arg3;
			return this;
		}
	}

	public static void main(String[] args) {
		new MethodDefineByBuilder().method(new MethodDefineByBuilder.Builder().setArg1(1).setArg2(2).setArg3("abc"));
		
	}

}
