package effctiveJava.section1;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicInteger;

public class Singleton implements Serializable{

	private static final long serialVersionUID = -478357682602657115L;
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	private static final Singleton instance = new Singleton();
	
	private Singleton() {
		//防止通过反射，new出新的实例
		if(count.get() != 0) {
			throw new IllegalStateException("Already have a instance!");
		}
		count.incrementAndGet();
	}
	
	public static Singleton getInstance () {
		return instance;
	}
	
	//防止通过反序列化，生成新实例
	private Object readResolve () {
		return instance;
	}
	
	public static void main(String[] args) {
		serializeSingleton();
		reflectSingleton();
	}
	
	public static void serializeSingleton() {
		try {
			ByteArrayOutputStream buff = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(buff);
			out.writeObject(Singleton.getInstance());
			out.close();
			
			byte[] objBytes = buff.toByteArray();
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(objBytes));
			Singleton instance = (Singleton) in.readObject();
			in.close();
			
			System.out.println(instance == Singleton.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static void reflectSingleton () {
		try {
			Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			Singleton instance = constructor.newInstance();
			System.out.println(instance == Singleton.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
