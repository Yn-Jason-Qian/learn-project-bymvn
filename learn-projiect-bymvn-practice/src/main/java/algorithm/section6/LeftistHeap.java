package algorithm.section6;

public class LeftistHeap <T extends Comparable<? super T>> {

	private static class Node<T> {
		Node<T> left;
		Node<T> right;
		int npl;
		T element;
		
		public Node(T element) {
			this(null, null, element);
		}

		public Node(Node<T> left, Node<T> right, T element) {
			this.left = left;
			this.right = right;
			this.element = element;
			this.npl = 0;
		}
		
	}
	
	private Node<T> root;
	
	public LeftistHeap () {
	}
	
}
