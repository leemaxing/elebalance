package com.liicon.utils.pair;

/**
 * 键值对（键为字符串）
 * @version  v0.2  king  2014-10-11  键值对
 */
public class StringValuePair<V> extends KeyValuePair<String, V> {
    private static final long serialVersionUID = 1L;
    
    public StringValuePair() {
        super();
    }
    public StringValuePair(String key, V value) {
        super(key, value);
    }
    


    @Override
    public String toString() {
        return "StringValuePair [key=" + getKey() + ", value=" + getValue() + "]";
    }

}
