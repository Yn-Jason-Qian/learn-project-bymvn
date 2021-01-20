package thinkinjava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//生产者消费者
public class Restaurant {
	static class Meal {
		private final int id;
		public Meal(int id) {	this.id = id;}
		@Override
		public String toString() {
			return String.format("Meal [id=%s]", id);
		}
	}
	
	static class Chef implements Runnable{
		Restaurant restaurant;
		private int count;
		public Chef(Restaurant restaurant) {	this.restaurant = restaurant;}
		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					synchronized(this) {
						while(restaurant.meal != null) {
							wait();
						}
					}
					if(count++ == 10) {
						System.out.println("closing Restaurant!");
						restaurant.close();
						return;
					}
					Thread.sleep(100);
					System.out.print("order up!");
					synchronized (restaurant.waitPerson) {
						restaurant.meal = new Meal(count);
						restaurant.waitPerson.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				System.out.println("interrupted!");
			}
		}
	}
	
	static class WaitPerson implements Runnable {
		Restaurant restaurant;
		public WaitPerson(Restaurant restaurant) {	this.restaurant = restaurant;}
		@Override
		public void run() {
			try {
				while(!Thread.interrupted()) {
					synchronized(this) {
						while(restaurant.meal == null) {
							wait();
						}
						System.out.printf("%s on%n", restaurant.meal);
						restaurant.meal = null;
					}
					Thread.sleep(100);
					synchronized (restaurant.chef) {
						restaurant.chef.notifyAll();
					}
				}
			} catch (InterruptedException e) {
				System.out.println("interrutped!");
			}
		}
		
	}
	
	public void close() {
		service.shutdownNow();
	}
	
	private Meal meal;
	Chef chef;
	WaitPerson waitPerson;
	ExecutorService service = Executors.newCachedThreadPool();
	public Restaurant() {
		chef = new Chef(this);
		waitPerson = new WaitPerson(this);
	}
	
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant();
		restaurant.service.execute(restaurant.chef);
		restaurant.service.execute(restaurant.waitPerson);
	}

}
