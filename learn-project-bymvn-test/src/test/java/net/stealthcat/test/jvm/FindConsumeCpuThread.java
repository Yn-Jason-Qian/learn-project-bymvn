package net.stealthcat.test.jvm;

public class FindConsumeCpuThread {

	public static void main(String[] args) {
		int cpuCount = Runtime.getRuntime().availableProcessors();
		for(int i=0;i<cpuCount;i++) {
			new Thread(new ConsumeCpuTask()).start();
		}
		for(int i=0;i<10;i++) {
			new Thread(new NotConsumeCpuTask()).start();
		}
	}
	
	static class ConsumeCpuTask implements Runnable {

		@Override
		public void run() {
			while(true) {
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		}
		
	}
	
	static class NotConsumeCpuTask implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(20 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
