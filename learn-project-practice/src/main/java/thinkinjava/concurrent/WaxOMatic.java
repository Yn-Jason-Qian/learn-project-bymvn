package thinkinjava.concurrent;

/*
 * 理想的结果应该是“wax car”和“buff car”交替出现，
 * 但是因为while语句类的流程不是线程安全的，使得任务可能在打印语句之前，切换执行线程，
 * 而出现连续打印“buff car”或者“wax car”。
 * 注释掉Thread.sleep()语句后，出现更加频繁。
 * 
 */
public class WaxOMatic {
	
	static class Car {
		private boolean waxOn = false;
		public synchronized void wax() {
			waxOn = true;
			notify();
		}
		public synchronized void buff() {
			waxOn = false;
			notify();
		}
		public synchronized void waitingForWax() throws InterruptedException {
			while(!waxOn) {
				wait();
			}
		}
		public synchronized void waitingForBuff() throws InterruptedException {
			while(waxOn) {
				wait();
			}
		}
	}
	
	static class WaxOn implements Runnable {
		private Car car;
		
		public WaxOn(Car car) {
			this.car = car;
		}

		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					car.wax();
					System.out.println("wax car");
//					Thread.sleep(100);
					car.waitingForBuff();
				}
			} catch (InterruptedException e) {
				System.out.println("interruptedException");
			}
		}
		
	}
	
	static class WaxOff implements Runnable {
		private Car car;
		
		public WaxOff(Car car) {
			this.car = car;
		}

		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					car.waitingForWax();
					car.buff();
					System.out.println("buff car");
//					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				System.out.println("interruptedException");
			}
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		Thread waxThread = new Thread(new WaxOn(car));
		Thread buffThread = new Thread(new WaxOff(car));
		waxThread.start();
		buffThread.start();
		Thread.sleep(3000);
		waxThread.interrupt();
		buffThread.interrupt();
	}

}
