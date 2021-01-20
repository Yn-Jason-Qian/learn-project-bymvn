package algorithm.section4;

import java.util.Iterator;

public class Test004_11 {

	
	
}

class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T>{
	
	private static class BinaryNode<T> {
		T value;
		BinaryNode<T> left;
		BinaryNode<T> right;
		BinaryNode<T> parent;
		public BinaryNode(T value, BinaryNode<T> left, BinaryNode<T> right, BinaryNode<T> parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}
	private BinaryNode<T> root;
	
	public void makeEmpty() {
		root = null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public boolean contains(T value) {
		return contains(root, value);
	}
	
	private boolean contains(BinaryNode<T> node, T value) {
		if(node == null) {
			return false;
		}
		int compareResult = node.value.compareTo(value);
		if(compareResult == 0) {
			return true;
		} else if(compareResult < 0){
			return contains(node.right, value);
		} else {
			return contains(node.left, value);
		}
	}
	
	public T findMax() {
		return findMax(root);
	}
	
	private T findMax(BinaryNode<T> node) {
		if(node == null) {
			return null;
		}
		if(node.right == null) {
			return node.value;
		} else {
			return findMax(node.right);
		}
	}
	
	public T findMin() {
		return findMin(root).value;
	}
	
	private BinaryNode<T> findMin(BinaryNode<T> node) {
		if(node == null) {
			return null;
		}
		if(node.left == null) {
			return node;
		} else {
			return findMin(node.left);
		}
	}
	
	public void insert(T value) {
		root = insert(root, value);
	}

	private BinaryNode<T> insert(BinaryNode<T> node, T value) {
		if(node == null) {
			return new BinaryNode<T>(value, null, null, null);
		}
		int compareResult = node.value.compareTo(value);
		if(compareResult > 0) {
			node.left = insert(node.left, value);
			node.left.parent = node;
		} else if(compareResult < 0) {
			node.right = insert(node.right, value);
			node.right.parent = node;
		}
		return node;
	}
	
	public void remove(T value) {
		root = remove(root, value);
	}
	
	private BinaryNode<T> remove(BinaryNode<T> node, T value) {
		if(node == null) {
			return null;
		}
		int compareResult = node.value.compareTo(value);
		if(compareResult < 0) {
			node.right = remove(node.right, value);
		} else if(compareResult > 0) {
			node.left = remove(node.left, value);
		} else {
			if(node.left != null && node.right != null) {
				node.value = findMin(node.right).value;
				node.right = remove(node.right, node.value);
			} else {
				node = node.left != null ? node.left : node.right;
			}
		}
		return node;
	}

	@Override
	public Iterator<T> iterator() {
		return new BinarySearchIterator();
	}
	
	private class BinarySearchIterator implements Iterator<T> {

		private BinaryNode<T> current;
		private boolean end;
		
		public BinarySearchIterator() {
			this.current = findMin(root);
		}

		@Override
		public boolean hasNext() {
			return !end;
		}

		@Override
		public T next() {
			T value = current.value;
			if(current.right != null) {
				current = findMin(current.right);
			} else {
				BinaryNode<T> child = current;
				current = current.parent;
				while(current != null && current.left != child) {
					child = current;
					current = current.parent;
				}
				if(current == null) {
					end = true;
				}
			}
			return value;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
