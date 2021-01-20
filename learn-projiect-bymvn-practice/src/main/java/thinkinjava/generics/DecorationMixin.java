package thinkinjava.generics;

import java.util.Date;

/*
 * 装饰器模式实现混型
 */
public class DecorationMixin {

	static class Basic {
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
	static class Decorator extends Basic{
		private Basic basic;

		public Decorator(Basic basic) {
			super();
			this.basic = basic;
		}

		@Override
		public String getValue() {
			return basic.getValue();
		}

		@Override
		public void setValue(String value) {
			basic.setValue(value);
		}
	}
	
	static class Timestamped extends Decorator {
		private final long timestamp; 
		
		public Timestamped(Basic basic) {
			super(basic);
			timestamp = new Date().getTime();
		}
		
		public long getTimestamp() {
			return timestamp;
		}
	}
	
	static class SerialNumbered extends Decorator {
		private static int count = 1;
		private final int number = count++;
		
		public SerialNumbered(Basic basic) {
			super(basic);
		}

		public int getNumber() {
			return number;
		}
	}
	
	public static void main(String[] args) {
		Timestamped timestamped = new Timestamped(new SerialNumbered(new Basic()));
		//timestamped.getNumber();不可用
		System.out.println(timestamped.getTimestamp());
		
		SerialNumbered serialNumbered = new SerialNumbered(new Timestamped(new Basic()));
		//serialNumbered.getTimestamp();不可用
		System.out.println(serialNumbered.getNumber());
	}

}
