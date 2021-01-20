package effctiveJava.section10;

public class StopThread {

	private static volatile boolean stopRequested;
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 0;
				while(!stopRequested) {
					i++;
				}
			}
		}).start();
		Thread.sleep(1000);
		stopRequested = true;
	}

}
