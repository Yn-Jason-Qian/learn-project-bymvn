package effctiveJava.section11;

import java.io.*;
import java.util.Date;

public class Period implements Serializable{

	private static final long serialVersionUID = -9178977005418669583L;

	private final Date start;
	
	private final Date end;

	public Period(Date start, Date end) {
		if(start.after(end)) {
			throw new IllegalArgumentException();
		}
		this.start = start;
		this.end = end;
	}
	
	private Object writeReplace() {
		return new SerializableProxy(this);
	}
	
	private void readObject(ObjectInputStream o) throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required!");
	}
	
	private static class SerializableProxy implements Serializable {
		private static final long serialVersionUID = -7917059322582761520L;

		private final Date start;
		
		private final Date end;

		public SerializableProxy(Period p) {
			start = p.start;
			end = p.end;
		}
		
		private Object readResolve() {
			System.out.println("proxy readResolve!");
			return new Period(start, end);
		}
	}
	
	@Override
	public String toString() {
		return String.format("Period [start=%s, end=%s]", start, end);
	}

	private static final String file_path = "/home/qianyang/temp/1.txt";
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		Date start = new Date();
		Period period = new Period(start, new Date(start.getTime() + 1000));
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file_path));
		out.writeObject(period);
		
		period = (Period) new ObjectInputStream(new FileInputStream(file_path)).readObject();
		System.out.println(period);
	}
}
