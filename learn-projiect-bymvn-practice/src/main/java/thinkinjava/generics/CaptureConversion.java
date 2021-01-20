package thinkinjava.generics;

//捕获转换
public class CaptureConversion {

	static <T> T f1(Holder<T> holder) {
		T t = holder.get();
//		holder.set(arg);
		System.out.println(t.getClass().getName());
		return t;
	}
	
	static void f2(Holder<?> holder) {
		f1(holder);
	}
	
	static void f3(Holder holder) {
		f1(holder);
	}
	
	public static void main(String[] args) {
		Holder holder = new Holder<Long>();
		holder.set(1L);
		f2(holder);
	}

}
