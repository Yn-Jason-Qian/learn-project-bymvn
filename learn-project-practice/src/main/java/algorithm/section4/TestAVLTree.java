package algorithm.section4;

public class TestAVLTree {

	public static void main(String[] args) {
		
	}
	
	private static class AvlTree<T extends Comparable<? super T>> {
		
		private static class AvlNode<T> {
			int height;
			AvlNode<T> left;
			AvlNode<T> right;
			T element;
			public AvlNode(int height, AvlNode<T> left, AvlNode<T> right, T element) {
				super();
				this.height = height;
				this.left = left;
				this.right = right;
				this.element = element;
			}
		}
		
		private AvlNode<T> root;
		
		private int height(AvlNode<T> node) {
			return node == null? -1 : node.height;
		}
		
		public void insert(T value) {
			root = insert(value, root);
		}
		
		private int compareHeight(AvlNode<T> left, AvlNode<T> right) {
			return Math.abs(height(left) - height(right));
		}
		
		private AvlNode<T> insert(T value, AvlNode<T> node) {
			if(node == null) {
				return new AvlNode<T>(0, null, null, value);
			}
			int compareResult = value.compareTo(node.element);
			if(compareResult > 0) {
				node.left = insert(value, node.left);
				if(compareHeight(node.left, node.right) == 2) {
					if(node.left.element.compareTo(value) > 0) {
						node = rotateWithLeftChild(node);
					} else if(node.left.element.compareTo(value) < 0) {
						node = doubleWithLeftRightChild(node);
					}
				}
			} else if(compareResult < 0) {
				node.right = insert(value, node.right);
				if(compareHeight(node.left, node.right) == 2) {
					if(node.left.element.compareTo(value) > 0) {
						node = rotateWithRightChild(node);
					} else if(node.left.element.compareTo(value) < 0) {
						node = doubleWithRightLeftChild(node);
					}
				}
			}
			node.height = Math.max(height(node.left), height(node.right)) + 1;
			return node;
		}
		
		private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
			AvlNode<T> k1 = k2.left;
			k2.left = k1.right;
			k1.right = k2;
			k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
			k1.height = Math.max(height(k1.left), k2.height) + 1;
			return k1;
		}
		
		private AvlNode<T> rotateWithRightChild(AvlNode<T> k2) {
			AvlNode<T> k1 = k2.right;
			k2.right = k1.left;
			k1.left = k2;
			k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
			k1.height = Math.max(k2.height, height(k1.right)) + 1;
			return k1;
		}
		
		private AvlNode<T> doubleWithLeftRightChild(AvlNode<T> k3) {
			k3.left = rotateWithRightChild(k3.left);
			return rotateWithLeftChild(k3);
		}
		
		private AvlNode<T> doubleWithRightLeftChild(AvlNode<T> k3) {
			k3.right = rotateWithLeftChild(k3.right);
			return rotateWithRightChild(k3);
		}
		
		public void remove(T value) {
			root = remove(value, root);
		}

		private AvlNode<T> remove(T value, AvlNode<T> t) {
			if(t == null) {
				return null;
			}
			int compareResult = value.compareTo(t.element);
			if(compareResult == 0) {
				if(t.left != null && t.right != null) {
					
				} else {
					t = t.left != null? t.left : t.right;
				}
			} else {
				
			}
			return null;
		}
	}

}
