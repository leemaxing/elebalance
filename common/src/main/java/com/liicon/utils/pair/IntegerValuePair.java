package com.liicon.utils.pair;

/**
 * 键值对（键为数值）
 * @version  v0.2  king  2014-10-11  键值对
 */
public class IntegerValuePair<V> extends KeyValuePair<Integer, V> {
    private static final long serialVersionUID = 1L;
    
    public IntegerValuePair() {
        super();
    }
    public IntegerValuePair(Integer key, V value) {
        super(key, value);
    }



    @Override
    public String toString() {
        return "IntegerValuePair [key=" + getKey() + ", value=" + getValue() + "]";
    }

}
