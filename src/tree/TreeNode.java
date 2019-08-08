package tree;
	
public class TreeNode<T extends Comparable<T>>{
	T element;
	TreeNode<T> left;
	TreeNode<T> right;
	
	public TreeNode(T o) {
		// TODO Auto-generated constructor stub
		this.element = o;
		this.left = null;
		this.right = null;
	}
 
	public TreeNode() {
		// TODO Auto-generated constructor stub
		this.element = null;
		this.left = null;
		this.right = null;
	}
	
	public void insert(T o) {
		if (element.compareTo(o) < 0) {
			if (right == null) {
				right = new TreeNode<T>(o);
			} else {
				right.insert(o);
			}
		} else if (element.compareTo(o) > 0) {
			if (left == null) {
				left = new TreeNode<T>(o);
			} else {
				left.insert(o);
			}
		}
	}
	
	public void visit() {
		System.out.print(this.element + " ");
	}
}