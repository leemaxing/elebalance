package com.liicon.utils.lang;

import android.annotation.SuppressLint;

/**
 * 类型转换-工具类
 * @version  v0.1  king  2014-11-18  类型转换处理
 */
public class Convert {

    /**
     * 字符串转整数
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串转长整形
     * @param obj
     * @param defValue 转换异常返回值
     * @return
     */
    public static long toLong(String obj, long defValue) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串转双精度浮点型
     * @param str
     * @param defValue
     * @return
     */
    public static double toDouble(String str, double defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串转布尔值
     * @param b
     * @param defValue 转换异常返回值
     * @return
     */
    public static boolean toBoolean(String b, boolean defValue) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 半角转全角
     * @param input String
     * @return 全角字符串
     */
    public static String toSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }
    
    /**
     * 将指定部分字符串转为大写
     * @param str
     * @param start
     * @param end
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String upperCase(String str, int start, int end) {
        if (str == null || str.length() == 0) {
            return null;
        } else if (start < 0 || end > str.length()) {
            return null;
        }
        
        return new StringBuilder(str.substring(0, start))
                .append(str.substring(start, end).toUpperCase())
                .append(str.substring(end)).toString();
    }

    /**
     * 二进制转换为十六进制
     * @param binary
     * @return
     */
    public static String binary2hex(String binary) {
        String hexStr[] = { "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F" };
        int length = binary.length();
        int temp = length % 4;

        if (temp != 0) {
            for (int i = 0; i < 4 - temp; i++) {
                binary = "0" + binary;
            }
        }

        length = binary.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length / 4; i++) {
            int num = 0;

            for (int j = i * 4; j < i * 4 + 4; j++) {
                num <<= 1;// 左移
                num |= (binary.charAt(j) - '0');
            }
            sb.append(hexStr[num]);

        }
        return sb.toString();
    }
    
    
    
}
