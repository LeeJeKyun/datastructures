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

    public Hash(int tableSize) {
        this.tableSize = tableSize;
        maxLoadFactor = 0.75;
        numElements = 0;
        harray = (LinkedList<HashElement<K,V>>[]) new LinkedList[tableSize];

        for(int i=0; i<tableSize; i++) {
            harray[i] = new LinkedList<HashElement<K, V>>();
        }
    }

    public boolean add(K key, V value) {
        if(loadFactor() > maxLoadFactor)
            resize(tableSize*2);

        HashElement<K,V> he = new HashElement<>(key, value);
        int hashval = key.hashCode();
        hashval &= 0x7fffffff;
        hashval %= tableSize;

        harray[hashval].add(he);
        numElements++;
        return true;
    }

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
