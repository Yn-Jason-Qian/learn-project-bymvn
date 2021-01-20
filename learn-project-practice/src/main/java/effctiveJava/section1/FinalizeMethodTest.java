package effctiveJava.section1;

public class FinalizeMethodTest {

	public static void main(String[] args) throws InterruptedException {
		new FinalizeObject();
		System.gc();
		Thread.sleep(2000);
	}
	
	static class SuperObject {

		//终结方法守护者，确保父类finalizer的执行
		private final Object finalizerGuardian = new Object() {

			@Override
			protected void finalize() throws Throwable {
				close();
			}
			
		};
		
		public void close() {
			System.out.println("invoke super close method!");
		}
		
	}

	static class FinalizeObject extends SuperObject{
		
		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			System.out.println("invoke finalize method");
			throw new RuntimeException();
		}
		
	}
}
