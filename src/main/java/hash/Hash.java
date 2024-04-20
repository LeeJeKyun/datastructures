package hash;

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
}
