package linkedlist;

import java.util.List;

/**
 * 포인터를 사용하여 여러 개의 노드를 연결하는 자료구조 - 연결 리스트
 * 기본 구성 요소는 노드이다. head는 리스트의 첫 번째 노드를 가리킨다.
 * 힙에서는 연결리스트의 head만 알고있기 때문에 head.next, head.data등으로 노드의 내용을 찾는다.
 * 연결 리스트의 길이가 매우 길 경우 계속 head 뒤에 next를 붙일 수는 없다. 따라서 임시 포인터를 사용하여 탐색하는 방법을 사용한다.
 * @param <E>
 */
public class LinkedList<E> {

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
    public LinkedList() {
        head=null;
        currentSize=0;
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
     * node.next=head(==null)
     * head=node
     * 시간복잡도: O(1)
     * @param data
     */
    public void addFirst(E data) {
        Node<E> node = new Node<E>(data);
        node.next=head;
        head=node;
        currentSize++;
    }

    /**
     * addLast: 노드를 마지막 위치에 추가할 때
     * 연결리스트의 노드들은 각자 next라는 변수에 다음 노드의 위치를 가지고 있다.
     * 따라서 연결리스트의 마지막 노드는 null이라는 위치를 가지고 있다.
     * 이를 이용해 마지막 노드를 tmp라는 변수에 대입 가능하다.
     * 그 후 마지막 노드의 next변수에 새로 생성한 node를 가르키게 만들면 마지막에 추가가 가능하다.
     * 그러나 경계조건에 따라 자료구조의 첫번째 요소를 추가할때 예외상황이 발생한다.
     * while문을 1번은 거치기 때문에 tmp.next에서 NullPointerException이 발생함.
     * 따라서 if문을 이용해 head가 null인 경우를 먼저 예외처리해준다.
     *
     * 시간복잡도: O(n)
     *
     * @param data
     */
    public void addLast(E data) {
        Node<E> node = new Node<E>(data);
        if(head == null) {
            head = node;
            currentSize++;
            return;
        }
        Node<E> tmp = head;
        while(tmp.next != null) tmp=tmp.next;
        tmp.next = node;
        currentSize++;
    }
}
