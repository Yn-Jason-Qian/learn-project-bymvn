package algorithm.section1;

import algorithm.Util;

import java.util.Comparator;

public class Test001_15 {

	private static class Rectangle {
		private int length;
		private int width;
		Rectangle(int length, int width) {
			this.length = length;
			this.width = width;
		}
		@Override
		public String toString() {
			return String.format("Rectangle [length=%s, width=%s]", length, width);
		}
	}
	
	private static class MyComparator implements Comparator<Rectangle> {

		@Override
		public int compare(Rectangle o1, Rectangle o2) {
			int area1 = o1.length * o1.width;
			int area2 = o2.length * o2.width;
			if(area1 > area2) {
				return 1;
			} else if(area1 < area2){
				return -1;
			} else {
				int girth1 = o1.length + o1.width;
				int girth2 = o2.length + o2.width;
				if(girth1 > girth2) {
					return 1;
				} else if(girth1 < girth2){
					return -1;
				}
			}
			return 0;
		}
		
	}
	
	static void findmax(Rectangle[] arr, Comparator<Rectangle> comparator) {
		Rectangle max = arr[0];
		for(int i=0; i< arr.length; i++) {
			System.out.println(arr[i]);
			if(comparator.compare(max, arr[i]) < 0) {
				max = arr[i];
			}
		}
		System.out.println("max : " + max);
	}
	
	public static void main(String[] args) {
		Rectangle[] arr = new Rectangle[10];
		for(int i=0; i<10; i++) {
			arr[i] = new Rectangle(Util.nextPostiveInt(10), Util.nextPostiveInt(10));
		}
		findmax(arr, new MyComparator());
	}

}
