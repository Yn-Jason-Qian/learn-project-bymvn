package thinkinjava.generics;

import com.common.model.Cat;
import com.common.model.Dog;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class CheckedList {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void oldStyleMethod(List probablyDogs) {
		probablyDogs.add(new Cat());
	}
	
	public static void main(String[] args) {
		List<Dog> dogs1 = Lists.newArrayList();
		List<Dog> dogs2 = Collections.checkedList(Lists.newArrayList(), Dog.class);
		oldStyleMethod(dogs1);
		
		try {
			oldStyleMethod(dogs2);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			Dog dog = dogs1.get(0);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
