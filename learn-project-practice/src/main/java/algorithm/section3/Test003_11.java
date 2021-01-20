package algorithm.section3;

public class Test003_11 {

	public static void main(String[] args) {
		OneWayList<String> list = new OneWayList<String>();
		list.addIfAbsent("124");
//		list.printList();
		list.addIfAbsent("123");
//		list.printList();
		list.addIfAbsent("231");
//		list.printList();
		list.addIfAbsent("125");
//		list.printList();
		list.addIfAbsent("321");
		list.printList();
		System.out.println(list.size());
		System.out.println(list.contains("123"));
		System.out.println(list.removeIfPresent("3215"));
	}
	
	private static class OneWayList<T extends Comparable<T>> {
		private int size;
		private Node<T> start;
		
		public OneWayList() {
			start = new Node<T>(null, null);
		}
		
		public int size() {
			return size;
		}
		
		public void printList() {
			Node<T> item = start.next;
			while(item != null) {
				System.out.println(item.value);
				item = item.next;
			}
		}
		
		public boolean contains(T value) {
			Node<T> item = start.next;
			while(item != null) {
				if(item.value.compareTo(value) == 0) {
					return true;
				}
				item = item.next;
			}
			return false;
		}
		
		public boolean addIfAbsent(T value) {
			Node<T> item = start.next, prev = start;
			while(item != null) {
				if(item.value.compareTo(value) == 0) {
					return false;
				} else if(item.value.compareTo(value) > 0) {
					prev.next = new Node<T>(value, item);
					size++;
					break;
				} else {
					prev = item;
					item = item.next;
					if(item == null) {
						prev.next = new Node<T>(value, null);
						size++;
						break;
					}
				}
			}
			if(size == 0) {
				start.next = new Node<T>(value, null);
				size++;	
			}
			return true;
		}
		
		
		public boolean removeIfPresent(T value) {
			Node<T> item = start.next, prev = start;
			while(item != null) {
				if(item.value.compareTo(value) == 0) {
					prev.next = item.next;
					return true;
				}
				prev = item;
				item = item.next;
			}
			return false;
		}
		
		private class Node<X> {
			X value;
			Node<X> next;
			Node(X value, Node<X> next) {
				this.value = value;
				this.next = next;
			}
		}
	}

}
