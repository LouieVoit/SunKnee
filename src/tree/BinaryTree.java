package tree;

public class BinaryTree<T extends Comparable<T>> {
	TreeNode<T> root;
	
	public BinaryTree() {
		super();
		this.root = new TreeNode<T>();
	}
 
	public BinaryTree(T o) {
		super();
		// TODO Auto-generated constructor stub
		TreeNode<T> node = new TreeNode<T>(o);
		this.root = node;
	}
	
	public void add(T o) {
		if (root == null) {
			root = new TreeNode<T>(o);
		} else {
			root.insert(o);
		}
	}
	
	public boolean isMember (TreeNode<T> node, T o) {
		if(node == null) return false;
		
		if (node.element.compareTo(o) == 0) {
			return true;
		} else if (node.element.compareTo(o) < 0) {
			isMember(node.right, o);
		} else {
			isMember(node.left, o);
		}
		return false;
	}
	
	public void preOrderHelper (TreeNode<T> node) {
		if (node != null) {
			node.visit();
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		}
	}
	
	public void inOrderHelper(TreeNode<T> node) {
		if (node != null) {
			inOrderHelper(node.left);
			node.visit();
			inOrderHelper(node.right);
		}
	}
	
	public void postOrderHelper(TreeNode<T> node) {
		if (node != null) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			node.visit();
		}
	}
	
	public void preOrder() {
		if (root != null) {
			preOrderHelper(root);
		}
	}
	
	public void inOrder() {
		if (root != null) {
			inOrderHelper(root);
		}
	}
	
	public void postOrder() {
		if (root != null) {
			postOrderHelper(root);
		}
	}
	
}