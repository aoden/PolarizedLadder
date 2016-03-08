import java.util.Iterator;

public interface Tree<E> {


	public boolean isEmpty();

	public Iterator<E> iterator();

	public boolean isRoot();

	public Iterable<Node<E>> children(Node<E> v);

	public boolean isExternal(Node<E> t);

	public int size();

	public int depth();
}

