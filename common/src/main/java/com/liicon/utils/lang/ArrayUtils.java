package com.liicon.utils.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import android.util.SparseIntArray;

/**
 * 数组处理工具类
 * @version  v0.1  king  2014-11-18  数组处理
 */
public class ArrayUtils {
    
    /**
     * 数组转集合
     * @param objs
     * @return
     */
    public static List<Object> asList(Object... values) {
        List<Object> list = new ArrayList<Object>();
        final int len = values.length;
        for (int i=0; i<len; i++)
            list.add(values[i]);
        return list;
    }
    /**
     * 数组转集合
     * @param strs
     * @return
     */
    public static List<String> asList(String... values) {
        List<String> list = new ArrayList<String>();
        final int len = values.length;
        for (int i=0; i<len; i++)
            list.add(values[i]);
        return list;
    }
    /**
     * 数组转集合
     * @param strs
     * @return
     */
    public static List<Integer> asList(int... values) {
        List<Integer> list = new ArrayList<Integer>();
        final int len = values.length;
        for (int i=0; i<len; i++)
            list.add(values[i]);
        return list;
    }
    
    /**
     * 以指定间隔符，输出数组内容
     * @param array
     * @return
     */
    public static String toString(Map<?, ?> map) {
        return map.toString();
    }
    /**
     * 以指定间隔符，输出数组内容
     * @param array
     * @return
     */
    public static <T>String toString(T[] array) {
        return "["+toString(array, ", ")+"]";
    }
    /**
     * 以指定间隔符，输出数组内容
     * @param array
     * @return
     */
    public static <T>String toString(T[] array, String space) {
        if (array == null || array.length == 0) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i != 0 && !TextUtils.isEmpty(space)) {
                sb.append(space);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static <T>boolean equals(T[] array1, T[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(int[] array1, int[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(long[] array1, long[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(byte[] array1, byte[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(double[] array1, double[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(float[] array1, float[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(short[] array1, short[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(boolean[] array1, boolean[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    public static boolean equals(char[] array1, char[] array2) {
        return java.util.Arrays.equals(array1, array2);
    }
    

    @SuppressWarnings("unchecked")
    public static <T>T newInstance(Class<T> clazz, int size) throws NegativeArraySizeException {
        return (T) Array.newInstance(clazz.getComponentType(), size);
    }
    
    public static <T>T[] copyOfRange(T[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static int[] copyOfRange(int[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static boolean[] copyOfRange(boolean[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static double[] copyOfRange(double[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static float[] copyOfRange(float[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static long[] copyOfRange(long[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static short[] copyOfRange(short[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static byte[] copyOfRange(byte[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    public static char[] copyOfRange(char[] array, int start, int end) {
        return from.java.util.Arrays.copyOfRange(array, start, end);
    }
    
    public static <T>T[] copyOf(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static int[] copyOf(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static boolean[] copyOf(boolean[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static double[] copyOf(double[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static float[] copyOf(float[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static short[] copyOf(short[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static char[] copyOf(char[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static byte[] copyOf(byte[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }
    public static long[] copyOf(long[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return copyOfRange(array, 0, array.length);
    }

    public static List<Integer> keys(SparseIntArray array) {
        List<Integer> keys = new ArrayList<Integer>();
        for (int i=0; i<array.size(); i++) {
            keys.add(array.get(i));
        }
        return keys;
    }
}
