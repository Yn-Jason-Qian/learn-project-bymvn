package thinkinjava.concurrent;

public class AtomicityTest implements Runnable{

	public static void main(String[] args) {
		AtomicityTest test = new AtomicityTest();
		new Thread(test).start();
		int val = 0;
		while(true) {
			if((val = test.getValue()) % 2 != 0) {
				System.out.println(val);
				System.exit(0);
			}
		}
	}

	private int i = 0;
	public int getValue() {
		return i;
	}
	
	public synchronized void evenIncrease() {
		i++;
		i++;
	}
	
	@Override
	public void run() {
		while(true) {
			evenIncrease();
		}
	}

}
