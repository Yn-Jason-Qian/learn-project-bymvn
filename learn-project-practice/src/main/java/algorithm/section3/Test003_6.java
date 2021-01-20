package algorithm.section3;

import java.util.Scanner;


public class Test003_6 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n, m;
		n = scanner.nextInt();
		m = scanner.nextInt();
		TwowayLinkedList<Integer> list = new TwowayLinkedList<>(n, m);
		list.execute();
	}

	private static class TwowayLinkedList<T> {
		private int m;
		private Node current;
		private int size;
		
		public TwowayLinkedList(int n, int m) {
			this.size = n;
			this.m = m;
			this.current = new Node<Integer>(1, null, null);
			Node tmp = this.current;
			for(int i=0; i<n-1; i++) {
				tmp.next = new Node<Integer>(i+2, tmp, null);
				tmp = tmp.next;
			}
			this.current.prev = tmp;
			tmp.next = this.current;
		}
		
		private void remove() {
			System.out.println("remove : " + current.value);
			Node prev = current.prev, next = current.next;
			prev.next = next;
			next.prev = prev;
			current = next;
			size--;
		}
		
		public void execute() {
			while(!over()) {
				for(int i=0; i<m; i++) {
					this.current = current.next;
				}
				remove();
			}
			System.out.println("winner : " + current.value);
		}
		
		private boolean over() {
			return this.size == 1;
		}
		
		private class Node<X> {
			X value;
			Node next;
			Node prev;
			public Node(X value, Node prev, Node next) {
				this.value = value;
				this.prev = prev;
				this.next = next;
			}
		}
		
	}
	
}
