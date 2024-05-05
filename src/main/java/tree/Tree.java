package tree;

/**
 * 트리 자료구조를 객체화한 클래스
 *
 * @param <E>
 */
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

        //obj값이 현재 노드보다 크거나 같을 경우 오른쪽으로간다.
        //만약 현재 노드의 right포인터가 null일경우 거기에 새로운 노드를 추가한다.
        //right포인터가 null이 아닐경우 해당 노드에서 add메서드를 다시 실행한다.
        if(((Comparable<E>)obj).compareTo(node.data)>=0){
            //go to the right
            if(node.right == null) {
                node.right = new Node(obj);
                return;
            }
            add(obj, node.right);
        }

        //
        if(node.left == null) {
            node.left = new Node(obj);
            return;
        }
        add(obj, node.left);
    }

    /**
     * root노드가 null일 경우를 대비한 오버로딩 메소드
     * root가 null일 경우 새로운 Node를 생성하여 포인팅한다.
     * 만약 null이 아닐경우 위에서 작성한 add메서드를 실행한다.
     * @param obj
     */
    public void add(E obj) {
        if(root == null)
            root = new Node<E>(obj);
        else
            add(obj, root);
        currentSize++;
    }

    /**
     *
     * @param obj
     * @param node
     * @return
     */
    private boolean contains(E obj, Node<E> node) {
        //node가 null이면 찾고자하는 obj가 없다는 뜻이니 false를 반환
        if(node == null)
            return false;
        //node값과 obj가 같으면 갖고있다는 의미이니 true를 반환
        if(((Comparable<E>)obj).compareTo(node.data)==0)
            return true;
        //obj값이 node의 data보다 클 경우 오른쪽 노드를 포인팅하여 재귀
        if(((Comparable<E>)obj).compareTo(node.data)>0){
            contains(obj, node.right);
        }
        //그 무엇도 아닐 경우 obj값이 node의 data보다 작다는 의미이니 왼쪽노드를 포인팅하여 재귀
        return contains(obj, node.left);
    }

    /**
     * root에서 시작하기위한 오버로딩 메소드
     * @param obj
     * @return
     */
    public boolean contains(E obj) {
        return contains(obj, root);
    }

    /**
     * 특정 데이터를 지닌 노드를 삭제하는 메서드
     * 1. Leaf노드의 경우,  부모 노드의 포인터가 null을 포인팅하게 변경
     * 2. 자식노드가 하나인 노드를 제거하는 경우, 부모노드의 포인터가 자식노드를 포인팅하게 변경
     * 3. 자식노드가 두개인 노드를 제거하는 경우, 중위후속자 혹은 중위선임자와 자리를 바꾼 후 제거한다.
     *      중위후속자 - 타겟에서 왼쪽1회 이후 오른쪽으로 계속갔을때의 leaf노드. 타겟보다 작은 노드들 중 가장 큰 노드
     *      중위선임자 - 타겟에서 오른쪽1회 이후 왼쪽으로 계속 갔을때의 leaf노드. 타겟보다 큰 노드들 중 가장 작은 노드
     */

    /**
     * 이진트리의 균형을 맞추는 메서드
     * 1. left 자식노드의 left subtree에서 불균형 -> right rotation(우측회전)을 이용한다
     * 2. right 자식노드의 right subtree에서 불균형 -> left rotation(좌측회전)을 이용한다.
     *      right rotation(우측회전) - 조부모 노드가 부모노드의 right노드가 된다.
     *      left rotation(좌측회전) - 조부모 노드가 부모노드의 left노드가 된다.
     * 3. left 자식노드의 right subtree에서 불균형 -> left-right rotation을 이용한다.
     * 4. right 자식노드의 left subtree에서 불균형 -> right-left rotation을 이용한다.
     */
    public Node<E> leftRotate(Node<E> node) {
        Node<E> tmp = node.right;
        node.right = tmp.left;
        tmp.left = node;
        return tmp;
    }

    public Node<E> rightRotate(Node<E> node) {
        Node<E> tmp = node.left;
        node.left = tmp.right;
        tmp.right = node;
        return tmp;
    }

    // right-left 회전에서 한번은 처음 right회전은 부모노드가, 다음 left회전은 조부모노드가 주체가되므로
    // 첫 rightRotate는 조부모노드의 오른쪽 노드(부모노드)가, 다음 leftRotate는 조부모노드(node)가 진행한다.
    public Node<E> rightLeftRotate(Node<E> node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }
    // left-right 회전에서 한번은 처음 left회전은 부모노드가, 다음 right회전은 조부모노드가 주체가되므로
    // 첫 leftRotate는 조부모노드의 오른쪽 노드(부모노드)가, 다음 rightRotate는 조부모노드(node)가 진행한다.
    public Node<E> leftRightRotate(Node<E> node) {
        node.left = leftRotate(node.left);
        return rightLeftRotate(node);
    }

}
