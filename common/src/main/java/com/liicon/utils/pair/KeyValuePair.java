package com.liicon.utils.pair;

import java.io.Serializable;
import java.util.Map.Entry;

/**
 * 键值对
 * @version  v0.2  king  2014-10-11  键值对
 */
public class KeyValuePair<K, V> implements Entry<K, V>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private K key;
    private V value;
    
    
    
    public KeyValuePair() {
        super();
    }
    public KeyValuePair(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        KeyValuePair<?, ?> other = (KeyValuePair<?, ?>) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }



    @Override
    public String toString() {
        return "KeyValue [key=" + key + ", value=" + value + "]";
    }



    
    /*
     * getter\setter
     */
    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public V getValue() {
        return value;
    }
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }
}
