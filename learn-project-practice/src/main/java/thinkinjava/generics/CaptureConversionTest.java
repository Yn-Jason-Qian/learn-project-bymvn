package thinkinjava.generics;

import java.util.ArrayList;
import java.util.List;

public class CaptureConversionTest {

	static List<?> f1(Holder<List<?>> holder, List<?> arg) {
		List<?> list = holder.get();
		holder.set(arg);
		return list;
	}
	
	static Holder<?> f2(List<Holder<?>> list, Holder<?> arg) {
		Holder<?> holder = list.get(0);
		list.add(arg);
		return holder;
	}
	
	public static void main(String[] args) {
		Holder<List<?>> holder = new Holder<List<?>>();
		f1(holder, new ArrayList<Long>());

	}

}
