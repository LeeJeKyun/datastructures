package hash;

public class HashTable {
    public int hash(String s) {
        int g=31;
        int hash = 0;
        for(int i=0; i<s.length(); i++){
            hash = g * hash + s.charAt(i);
        }

        int hashval = hash & 0x7FFFFFFF;
        //hashval %= tableSize;
        return hash;
    }
}
