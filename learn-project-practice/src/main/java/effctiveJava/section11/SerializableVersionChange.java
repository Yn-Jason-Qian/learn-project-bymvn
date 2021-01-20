package effctiveJava.section11;

import java.io.*;

public class SerializableVersionChange {
	static String filepath = "/home/qianyang/temp/1.txt";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		write2File();
		readObject();
	}
	
	static void write2File() throws IOException {
		Entity entity = new Entity();
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath));
		out.writeObject(entity);
		out.close();
		System.out.println("write success!");
	}

	static void readObject() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream out = new ObjectInputStream(new FileInputStream(filepath));
		Entity entity = (Entity) out.readObject();
		out.close();
		System.out.println("read success!" + entity);
	}
}
