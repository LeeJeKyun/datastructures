package hash;

import java.util.Iterator;
import java.util.LinkedList;

public class Hash<K, V> {

    /**
     * 기존 자바의 HashMap에서 Entry객체와 동일한 객체로 보면 된다.\
     * 내부에 Key, Value를 지니고 있고 Comparable인터페이스를 구현하여 Key값이 같으면 동일한 객체로 본다.
     * 객체를 생성할 때 Key, Value를 초기화한다.
     * @param <K>
     * @param <V>
     */
    class HashElement<K, V> implements Comparable<HashElement<K, V>> {
        K key;
        V value;
        public HashElement(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(HashElement<K, V> o) {
            return (((Comparable<K>)this.key).compareTo(o.key));
        }
    }

    // 객체의 갯수 및 Array 크기
    int numElements, tableSize;

    // 람다로 표현되는 적재율(Entry의 갯수 / 테이블사이즈(Array사이즈)
    double maxLoadFactor;
    LinkedList<HashElement<K,V>>[] harray;

    /**
     * 생성자
     * tableSize를 매개변수로 전달받아 tableSize크기의 LinkedList-Array를 만든다.
     * LoadFactor = HashElement의 개수 / 전체 Array의 길이
     * maxLoadFactor는 보통 0.6~0.7사이로 설정한다.
     * numElements는 요소(HashElement)의 갯수이다. 생성자이므로 0으로 초기화한다.
     * 배열의 인덱스마다 LinkedList를 초기화한다.(인스턴스 생성한다.)
     *
     * @param tableSize
     */
    public Hash(int tableSize) {
        this.tableSize = tableSize;
        maxLoadFactor = 0.75;
        numElements = 0;
        harray = (LinkedList<HashElement<K,V>>[]) new LinkedList[tableSize];

        for(int i=0; i<tableSize; i++) {
            harray[i] = new LinkedList<HashElement<K, V>>();
        }
    }

    /**
     * key, value값을 받아서 추가하는 매서드이다.
     * loadFactor를 계산하여 maxLoadFactor보다 작을 경우만 적용한다.
     * 만약 loadFactor가 maxLoadFactor를 넘어섰을 경우 사이즈를 2배로 늘린다.
     * @param key
     * @param value
     * @return
     */
    public boolean add(K key, V value) {
        if(loadFactor() > maxLoadFactor)
            resize(tableSize*2);

        HashElement<K,V> he = new HashElement<>(key, value);
        int hashval = key.hashCode();   //hashCode() 메서드를 이용해 int값으로 변경한다.
        hashval &= 0x7fffffff;  //&연산을 이용해 int값을 양수로 변경해준다.
        hashval %= tableSize;   //모듈러 연산을 통해 int정수를 array Index로 변경해준다.

        harray[hashval].add(he);    //위 연산의 결과인 array Index의 인스턴스(LinkedList)에 방금 생성한 HashElement를 추가한다.
        numElements++;  //전체 요소의 크기를 1 증가시킨다.
        return true;
    }

    /**
     * 입력된 key값의 hash코드를 찾아낸다.
     * hash
     * @param key
     * @param value
     * @return
     */
    public boolean remove(K key, V value) {
        int hashval = key.hashCode();
        hashval &= 0x7fffffff;
        hashval %= tableSize;
        HashElement<K, V> he = new HashElement<>(key, value);
        harray[hashval].remove(he);

        numElements--;
        return true;
    }

    public V getValue(K key) {
        int hashval = key.hashCode() & 0x7fffffff % tableSize;
        for(HashElement<K, V> he : harray[hashval]) {
            if(((Comparable<K>)key).compareTo(he.key) ==0 ){
                return he.value;
            }
        }
        return null;
    }

    public void resize(int newSize) {
        //새로운 배열(사이즈업된 배열)
        LinkedList<HashElement<K, V>>[] newArray = (LinkedList<HashElement<K,V>>[]) new LinkedList[newSize];
        //새로운 배열 초기화
        for (int i=0; i<newSize; i++)
            newArray[i] = new LinkedList<>();

        //기존 배열에 있던 요소들 새로운 배열에 추가
        for (IteratorHelper<K> it = new IteratorHelper<K>(); it.hasNext(); ) {
            K key = it.next();
            System.out.println("key = " + key);
            V val = getValue(key);
            HashElement<K, V> he = new HashElement<>(key, val);
            int hashval = (key.hashCode() & 0x7fffffff) % newSize;
            newArray[hashval].add(he);
        }

        harray = newArray;
        tableSize = newSize;
    }

    class IteratorHelper<T> implements Iterator<T> {
        T[] keys;
        int position;
        public IteratorHelper() {
            keys = (T[])new Object[numElements];
            int p=0;
            for(int i=0; i<tableSize; i++) {
                LinkedList<HashElement<K,V>> list = harray[i];
                for(HashElement<K,V> h : list)
                    keys[p++] = (T)h.key;
            }
            position=0;
        }
        @Override
        public boolean hasNext() {
            return position<keys.length;
        }

        @Override
        public T next() {
            if(!hasNext()) return null;
            return keys[position++];
        }
    }
    public double loadFactor() {
        return numElements/tableSize;
    }

    public static void main(String[] args) {
        Hash<String, Integer> hash = new Hash<>(5);
        hash.add("a", 1);
        hash.add("b", 2);
        hash.add("c", 3);
        hash.add("d", 4);
        hash.add("e", 5);
        hash.add("f", 6);
    }
}
