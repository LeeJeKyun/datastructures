package tree;

public class Tree<E> {

    Node<E> root;
    int currentSize;

    /**
     * Tree에서 각각의 노드를 나타내는 Node 내부 클래스
     * @param <E>
     */
    class Node<E> {
        E data;
        Node<E> left, right;
        public Node(E obj) {
            this.data=obj;
            left = right = null;
            currentSize = 0;
        }
    }

    /**
     * 트리에 노드를 추가하는 메소드
     *
     * @param obj - 추가되는 Node의 data
     * @param node - 현재 위치의 Node
     */
    private void add(E obj, Node<E> node) {
        if(((Comparable<E>)obj).compareTo(node.data)>=0){
            //go to the right
            if(node.right == null) {
                node.right = new Node(obj);
                return;
            }
            add(obj, node.right);
        }
        if(node.left == null) {
            node.left = new Node(obj);
            return;
        }
        add(obj, node.left);
    }
    public void add(E obj) {
        if(root == null)
            root = new Node<E>(obj);
        else
            add(obj, root);
        currentSize++;
    }

    private boolean contains(E obj, Node<E> node) {
        if(node == null)
            return false;
        if(((Comparable<E>)obj).compareTo(node.data)==0)
            return true;
        if(((Comparable<E>)obj).compareTo(node.data)>0){
            contains(obj, node.right);
        }
        return contains(obj, node.left);
    }

    public boolean contains(E obj) {
        return contains(obj, root);
    }

}
