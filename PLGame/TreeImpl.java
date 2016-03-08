
import java.util.ArrayList;
import java.util.Iterator;

public class TreeImpl<E> implements Tree<E> {

	  private Node<E> root;

	public TreeImpl(E rootData) {
		root = new Node<E>();
	        root.setData(rootData);
	        root.setChildren(new ArrayList<Node<E>>());
	    }
	
	public boolean isEmpty(){ 
		
		return (root==null);
	}

	public boolean isRoot(){
		     return (root.getParent()==null);
	}
	
	
	public Iterable<Node<E>>children(Node<E> v){
		return v.getChildren();
	}
	
	public Iterator<E> iterator(){
		return null;
		
	}
	
	public boolean isExternal(Node<E> t){
		 return (t.getChildren().isEmpty());
	}
	public int size(){
		return 1;
	}
	
	public int depth(){
		return 1;
	}
	
    public Node<E> getRoot() {
		return root;
	}

	public void setRoot(Node<E> root) {
		this.root = root;
	}

}
