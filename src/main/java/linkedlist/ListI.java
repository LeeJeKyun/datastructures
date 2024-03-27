package linkedlist;

public interface ListI<E> {
    public void addFirst(E data);
    public void addLast(E data);
    public E removeFirst();
    public E removeLast();
    public E remove(E search);
    public boolean contains(E search);
    public E peekFirst();
    public E peekLast();
}
