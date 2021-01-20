package thinkinjava.concurrent;

public class JoinTest {

	private static class Sleeper extends Thread{
		private final int sleepTime;
		public Sleeper(String name, int sleepTime) {
			super(name);
			this.sleepTime = sleepTime;
			start();
		}

		@Override
		public void run() {
			try {
				sleep(sleepTime);
				System.out.println("Sleeper " + getName() + " over");
			} catch (InterruptedException e) {
				System.out.println("Sleeper " + getName() + " interrupted : " + isInterrupted());
			}
		}
	}
	
	private static class Joineder extends Thread {
		private final Sleeper sleeper;

		public Joineder(Sleeper sleeper, String name) {
			super(name);
			this.sleeper = sleeper;
			start();
		}

		@Override
		public void run() {
			try {
				sleeper.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Joineder " + getName() + " over");
		}
		
	}
	
	public static void main(String[] args) {
		Sleeper grumpy = new Sleeper("grumpy", 1500),
				sleepy = new Sleeper("sleepy", 1500);
		Joineder dopey = new Joineder(grumpy, "dopey"),
				doc = new Joineder(sleepy, "doc");
		grumpy.interrupt();
	}

}
