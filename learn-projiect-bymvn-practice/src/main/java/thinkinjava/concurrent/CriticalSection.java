package thinkinjava.concurrent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


//临界区，同步代码块
public class CriticalSection {

	static class Pair {
		private int x;
		private int y;
		
		public Pair() {
		}
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public void incrementX() {x++;}
		public void incrementY() {y++;}

		@SuppressWarnings("serial")
		static class PairNotEqualsException extends RuntimeException {
		}
		
		public void checkState(){
			if(x != y) {
				throw new PairNotEqualsException();
			}
		}

		@Override
		public String toString() {
			return String.format("x=%s, y=%s", x, y);
		}
		
	}
	
	static abstract class PairManager {
		AtomicInteger count = new AtomicInteger();
		protected Pair pair = new Pair();
		private List<Pair> storage = new CopyOnWriteArrayList<>();
		
		public synchronized Pair getPair() {
			return new Pair(pair.getX(), pair.getY());
		}
		
		public void store(Pair pair) {
			storage.add(pair);
		}
		
		public abstract void increment();

		@Override
		public String toString() {
			return String.format("PairManager : count[%s] ,Pair[%s]",count.get(), getPair());
		}
		
	}
	
	static class PairManager1 extends PairManager{

		@Override
		public synchronized void increment() {
			pair.incrementX();
			pair.incrementY();
			store(getPair());
		}
		
	}
	
	static class PairManager2 extends PairManager {

		@Override
		public void increment() {
			Pair temp;
			synchronized (this) {
				pair.incrementX();
				pair.incrementY();
				temp = getPair();
			}
			store(temp);
		}
		
	}
	
	static class ExplicitPairManager1 extends PairManager {
		private ReentrantLock lock = new ReentrantLock();
		@Override
		public void increment() {
			lock.lock();
			try {
				pair.incrementX();
				pair.incrementY();
				store(getPair());
			} finally {
				lock.unlock();
			}
		}
		@Override
		public Pair getPair() {
			lock.lock();
			try {
				return super.getPair();
			} finally {
				lock.unlock();
			}
		}
	}
	
	static class ExplicitPairManager2 extends PairManager {
		private static ReentrantLock lock = new ReentrantLock();
		@Override
		public void increment() {
			lock.lock();
			Pair temp;
			try {
				pair.incrementX();
				pair.incrementY();
				temp = getPair();
			} finally {
				lock.unlock();
			}
			store(temp);
		}
		
		@Override
		public Pair getPair() {
			lock.lock();
			try {
				return super.getPair();
			} finally {
				lock.unlock();
			}
		}
	}
	
	static class PairManipulator implements Runnable {

		private PairManager pairManager;
		
		public PairManipulator(PairManager pairManager) {
			this.pairManager = pairManager;
		}

		/*
		 * 因为PairManager2的increment方法中，store操作不在同步块中，那么线程在执行完同步块时，cpu挂起当前线程的几率从0到有了。
		 * 从而使得PairCheckor的任务执行次数增多
		 * 
		 */
		@Override
		public void run() {
			while(true) {
				pairManager.increment();
			}
		}
		
	}
	
	static class PairCheckor implements Runnable {
		
		private PairManager pairManager;
		
		public PairCheckor(PairManager pairManager) {
			this.pairManager = pairManager;
		}

		@Override
		public void run() {
			while(true) {
				pairManager.count.incrementAndGet();
				pairManager.getPair().checkState();
			}
		}
		
	}
	
	private static void testApproaches(PairManager p1, PairManager p2) {
		ExecutorService service = Executors.newCachedThreadPool();
		PairManipulator pm1 = new PairManipulator(p1),
						pm2 = new PairManipulator(p2);
		PairCheckor pc1 = new PairCheckor(p1),
					pc2 = new PairCheckor(p2);
		service.execute(pm1);
		service.execute(pm2);
		service.execute(pc1);
		service.execute(pc2);
		
	}
	
	public static void main(String[] args) {
		PairManager p1 = new PairManager1();
		PairManager p2 = new PairManager2();
		testApproaches(p1, p2);
		
		PairManager p3 = new ExplicitPairManager1();
		PairManager p4 = new ExplicitPairManager2();
		testApproaches(p3, p4);
		
		final List<PairManager> managers = Lists.newArrayList(p1, p2, p3, p4);
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				for(PairManager manager : managers) {
					System.out.println(manager.getClass().getSimpleName() + " : " + manager);
				}
				System.exit(0);
			}
		}, 5000);
	}
}
