package linkedlist;

public class TestApp {
    public static void main(String[] args) {
        LinkedList_addTail<String> list = new LinkedList_addTail<>();
        list.addFirst("AB");
        list.addLast("BC");
        if(list.contains("BC")) System.out.println(list.remove("AB"));
    }
}
