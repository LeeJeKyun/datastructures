package linkedlist;

/**
 * 포인터를 사용하여 여러 개의 노드를 연결하는 자료구조 - 연결 리스트
 * 기본 구성 요소는 노드이다. head는 리스트의 첫 번째 노드를 가리킨다.
 * 힙에서는 연결리스트의 head만 알고있기 때문에 head.next, head.data등으로 노드의 내용을 찾는다.
 * 연결 리스트의 길이가 매우 길 경우 계속 head 뒤에 next를 붙일 수는 없다. 따라서 임시 포인터를 사용하여 탐색하는 방법을 사용한다.
 * @param <E>
 */
public class LinkedList_addTail<E> {

    /**
     * 노드를 내부 클래스로 선언하여 외부의 접근을 막는다. 자료구조가 깨어지지 않게 해줌.
     */
    class Node<E> {

        //제네릭을 이용해 data의 타입을 특정하지 않았다.
        E data;
        //Node<E>변수를 선언해 다음 노드의 포인터를 저장할 수 있다.
        Node<E> next;

        //Node를 생성할때 제네릭 타입의 데이터를 받아서 저장하고 다음 포인터는 null을 가르킨다.
        public Node(E data) {
            this.data =data;
            next=null;
        }
    }
    /**
     * head라는 노드와 현재 사이즈를 필드로 가지고 있다.
     * 현재 사이즈를 매번 연산할수도 있지만, O(n)의 연산이므로 효율적이지 못하다.
     * 그 대신 currentSize를 필드에 가지고있고 노드에 변경이 생길때마다 미리 계산해두면 O(1)연산으로 마무리 할 수 있다.
    */
    private Node<E> head;
    private int currentSize;
    private Node<E> tail;
    public LinkedList_addTail() {
        head=null;
        currentSize=0;
        tail=null;
    }

    /**
     * 어떤 자료구조든 경계조건에서 문제가 생기진 않을지 생각해봐야 한다.
     * 1. 자료구조가 비어있는 경우
     * 2. 자료구조에 단 하나의 요소가 들어있을 때
     * 3. 자료구조의 첫 번째 요소를 제거하거나 추가할 때
     * 4. 자료구조의 마지막 요소를 제거하거나 추가할 때
     * 5. 자료구조의 중간 부분을 처리할 때
     * @param obj
     */

    /**
     * addFirst: 노드를 첫위치에 추가할 때
     * 추가될 노드를 먼저 생성
     * 경계조건 -> 자료구조가 비어있는 경우 : head == null를 통해 알아낼 수 있음
     * head=node, tail=node후 사이즈 + 1
     * 아닌 경우 : node.next=head;(head는 현재 추가 전 처음 노드의 포인터 따라서 node.next=firstNode와 같다)
     * head=node
     * 시간복잡도: O(1)
     * @param data
     */
    public void addFirst(E data) {
        Node<E> node = new Node<E>(data);
        if(head == null) {
            head=node;
            tail=node;
            currentSize++;
            return;
        }
        node.next=head;
        head=node;
        currentSize++;
    }

    /**
     * addLast: 노드를 마지막 위치에 추가할 때(tail을 추가한 상태)
     * tail.next에 새로운 노드를 대입하여 추가가 가능하다.
     *
     * 시간복잡도: 0(1)
     *
     * @param data
     */
    public void addLast(E data) {
        Node<E> node = new Node<E>(data);
        if(head == null) {
            head = node;
            tail = node;
            currentSize++;
            return;
        }
        tail.next = node;
        tail = node;
        currentSize++;
    }
    public E removeFirst() {
        if(head ==null ) return null;
        E tmp = head.data;
        if(head == tail) head = tail = null;
        else head = head.next;
        currentSize--;
        return tmp;
    }
    public E removeLast() {
        if(head == null) return null;
        if(head == tail) return removeFirst();
        Node<E> current = head, previous = null;
        while(current != tail) {
            previous=current;
            current=current.next;
        }
        previous.next=null;
        tail=previous;
        currentSize--;
        return current.data;
    }
    public E remove(E search) {
        Node<E> current = head, previous = null;
        while(current != null) {
            if(((Comparable<E>)current.data).compareTo(search)==0){
                if(current == head) return removeFirst();
                if(current == tail) return removeLast();
                currentSize--;
                previous.next=current.next;
                return current.data;
            }
            previous=current;
            current=current.next;
        }
        return null;
    }

    public boolean contains(E search) {
        Node<E> current = head;
        while(current != null){
            if(((Comparable<E>)current.data).compareTo(search)==0) return true;
            current=current.next;
        }
        return false;
    }
}
