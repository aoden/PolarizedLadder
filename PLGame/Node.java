import java.util.ArrayList;

public class Node<T> {
	
	private int heuristic;
    private T data;
    private Node<T> parent;
	private ArrayList<Node<T>> children;
    public Node()
    {
    	children = new ArrayList<Node<T>>();
    }

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

	public Node<T> getParent() {
		return parent;
	}
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public ArrayList<Node<T>> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Node<T>> children) {
		this.children = children;
	}
	
	public void addChild(Node<T> child){
		children.add(child);
	}
}
