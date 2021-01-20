package thinkinjava.concurrent;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
	
	static class DelayedTask implements Delayed {
		private final int id;
		private final int delta;
		private final long trigger;
		public DelayedTask(int id, int delta) {
			this.id = id;
			this.delta = delta;
			this.trigger = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(delta);
		}

		@Override
		public int compareTo(Delayed o) {
			if(!(o instanceof DelayedTask)) {
				throw new IllegalArgumentException();
			}
			DelayedTask task = (DelayedTask) o;
			
//			return id < task.id ? -1 : (id > task.id ? 1 : 0);
			return delta < task.delta ? -1 : (delta > task.delta ? 1 : 0);
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.toNanos(trigger - System.nanoTime());
		}

		@Override
		public String toString() {
			return String.format("DelayedTask [id=%s, delta=%s]", id, delta);
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		DelayQueue<DelayedTask> queue = new DelayQueue<>();
		Random random = new Random();
		for(int i = 0; i < 10; i++) {
			queue.add(new DelayedTask(i, random.nextInt(5000)));
		}
		long start = System.currentTimeMillis();
		while(queue.size() != 0) {
			System.out.println(queue.take() + ", take time: " + (System.currentTimeMillis() - start));
		}
	}

}
