package thinkinjava.generics;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ThrowGenericException {
	
	static abstract class Processor<T, E extends Exception, E1 extends Throwable> {
		abstract void process(List<T> resultList) throws E, E1;
	}
	
	@SuppressWarnings("serial")
	static class ProcessorRunner<T, E extends Exception, E1 extends Throwable> 
		extends ArrayList<Processor<T, E, E1>> {
		List<T> processAll() throws E, E1 {
			List<T> result = Lists.newArrayList();
			for(Processor<T, E, E1> processor : this) {
				processor.process(result);
			}
			return result;
		}
	}
	
	@SuppressWarnings("serial")
	static class Exception1 extends Exception {}
	
	@SuppressWarnings("serial")
	static class Exception2 extends Exception {}
	
	static class Processor1 extends Processor<String, Exception1, Exception2> {

		static int count = 3;
		
		@Override
		void process(List<String> resultList) throws Exception1, Exception2 {
			if(count-- > 0) {
				resultList.add("3");
			}
			if(count == 0) {
				throw new Exception1();
			}
			if(count < 0) {
				throw new Exception2();
			}
		}
		
	}

	public static void main(String[] args) {
		ProcessorRunner<String, Exception1, Exception2> runner = new ProcessorRunner<>();
		for(int i=0;i<4;i++) {
			runner.add(new Processor1());
		}
		try {
			System.out.println(runner.processAll());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	static <E extends RuntimeException> void process() throws E {}
	
//	static class Exception3<T> extends Exception{}

	static class GenericException<T> extends ThrowGenericException{
		//No exception of type T can be thrown; an exception type must be a subclass of Throwable
		//Cannot use the type parameter T in a catch block
		void test() {
//			try {
//				
//			} catch (T e) {
//				
//			}
		}
		
	}
}
