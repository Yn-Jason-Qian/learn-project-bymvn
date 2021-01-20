package thinkinjava.generics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class NeedCasting {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(""));
		@SuppressWarnings("unchecked")
		List<Fruit> list = List.class.cast(in.readObject());
	}

}
