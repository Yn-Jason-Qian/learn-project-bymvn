package thinkinjava.concurrent;

public class LiftOff implements Runnable{
	private long countdown = 10;
	private static int count = 1;
	private final int id = count++;
	public LiftOff() {}
	public LiftOff(long countdown) {
		this.countdown = countdown;
	}

	private String status() {
		return String.format("#%s(%s)", id, countdown > 0 ? countdown : "LiftOff!");
	}
	
	@Override
	public void run() {
		while(countdown-- > 0) {
			System.out.println(status());
			Thread.yield();
		}
	}
	
	@Override
	public String toString() {
		return String.format("LiftOff [id=%s]", id);
	}
	
	public static void main(String[] args) {
		for(int i=0;i<5;i++) {
			new Thread(new LiftOff()).start();
		}
	}

}
