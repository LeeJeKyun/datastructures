package linkedlist;

import java.util.LinkedList;

public class TestApp {
    public static void main(String[] args) {
        LinkedList_addTail<Integer> list = new LinkedList_addTail<>();
        int n=10;
        for(int i=0; i<n; i++) list.addFirst(i);

        for(int i=n-1; i>=0; i--) {
            Integer x =list.removeFirst();
        }

    }
}
