package thinkinjava.generics;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class GenericCollections {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<? extends Fruit> flist = new ArrayList<Apple>();
//		flist.add(new Apple());	
//		flist.add(new Fruit());
//		flist.add(new Object());
		flist.add(null);
		((List<Apple>) flist).add(new Apple());
		
		
		List<? super Apple> alist = Lists.newArrayList();
		alist.add(new Apple());
		alist.add(new Jonathan());
	}
	
}

class Fruit {
}

class Apple extends Fruit {
}

class Jonathan extends Apple {
}

class Orange extends Fruit {
}
