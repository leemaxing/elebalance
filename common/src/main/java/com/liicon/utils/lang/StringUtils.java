package com.liicon.utils.lang;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import com.liicon.utils.version.SdkVersion;
import com.liicon.utils.version.SdkVersionCodes;

/**
 * 字符串操作工具类
 * @version  v0.1  king  2014-11-10  补充完善
 * @version  v0.1  king  2013-09-02  常用字符串处理方法
 */
@SuppressLint("DefaultLocale")
public class StringUtils {
    public static final String URL_REG_EXPRESSION = "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*";
    public static final String EMAIL_REG_EXPRESSION = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
	
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static byte[] getBytes(String str, Charset charset) {
        if (SdkVersion.isSupport(SdkVersionCodes.GINGERBREAD)) {
            return str.getBytes(charset);
        } else {
            try {
                return str.getBytes(charset.name());
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }
    
    @SuppressLint("NewApi")
	public static String newString(byte[] data, int offset, int byteCount, Charset charset) {
    	if (SdkVersion.isSupport(SdkVersionCodes.HONEYCOMB)) {
    		return new String(data, offset, byteCount, charset);
    	} else {
    		if ((offset | byteCount) < 0 || byteCount > data.length - offset) {
                throw new StringIndexOutOfBoundsException("length=" + data.length
                		+ "; regionStart=" + offset
                        + "; regionLength=" + byteCount);
            }
    		
    		byte[] copyData = Arrays.copyOfRange(data, offset, offset + byteCount);
    		return new String(copyData, charset);
		}
    }
	
    
    /**
     * 判断给定字符串是否空白串
     * <pre>
     * 忽略中英文空格、特殊字符（\r\t\n）
     * </pre>
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (TextUtils.isEmpty(input)) {
            return true;
        }
        
        if (trimSpecial(input).length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(StringUtils.trim(email)))
            return false;
        return Pattern.matches(URL_REG_EXPRESSION, email);
    }
    /**
     * 是否是URL
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        if (StringUtils.isEmpty(StringUtils.trim(url))) {
            return false;
        }
        return Pattern.matches(URL_REG_EXPRESSION, url);
    }
    /**
     * 判断是不是一个合法的图片路径
     * <pre>
     * 识别后缀：.jpg/.png/.gif/.bmp/.jpeg/.tiff/.ico
     * </pre>
     * @param imagePath
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static boolean isImage(String imagePath) {
        if (StringUtils.isEmpty(StringUtils.trim(imagePath)))
            return false;
        
        imagePath = imagePath.toLowerCase();
        if (imagePath.endsWith(".jpg")
                || imagePath.endsWith(".png")
                || imagePath.endsWith(".gif")
                || imagePath.endsWith(".bmp")
                || imagePath.endsWith(".jpeg")
                || imagePath.endsWith(".tiff")
                || imagePath.endsWith(".ico")) {
            if (imagePath.indexOf(".") != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将数字转换为，带N位小数点的字符
     * <p>
     * 例子：getDecimal(12.3, 2)  ==> "12.30"
     * 例子：getDecimal(12.345, 2)  ==> "12.34"
     * </p>
     * @param decimal 原始数值
     * @param length 要保留到小数点后的位数
     */
    public static String getDecimal(double decimal, int length) {
        String strLen = ".";
        if (length <= 0) {
            strLen = "";
        } else {
            for (int i=0; i<length; i++) {
                strLen += "0";
            }
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("0"+strLen);  
        return df.format(decimal);
    }

    /**
     * 去空格（中、英文空格）
     * @param charSequence
     * @return 返回非null字符串
     */
    public static String trim(CharSequence charSequence) {
        return charSequence==null ? "" : charSequence.toString().replace(" ", "").replace("　", "").trim();
    }
    /**
     * 去空格（中、英文空格）、特殊字符（\r\t\n）
     * @param charSequence
     * @return
     */
    public static String trimSpecial(CharSequence charSequence) {
        return trim(charSequence).replace("\r", "").replace("\t", "").replace("\n", "");
    }
    
    
    /**
     * 查找所有索引
     * @param str
     * @param searchStr
     * @return
     */
    public static Integer[] findAllIndexes(String str, String searchStr) {
        List<Integer> list = new ArrayList<Integer>();

        int index = str.indexOf(searchStr);
        while (index >= 0) {
            list.add(index);
            index = str.indexOf(searchStr, index + searchStr.length());
        }
        return list.toArray(new Integer[list.size()]);
    }
    
    
    /**
     * 格式化文件大小
     * @param number 文件大小，单位：byte
     * @return 显示小数点后2位
     */
    public static String formatFileSize(long number) {
        return formatFileSize(number, false);
    }
    /**
     * 格式化文件大小【G M K B】
     * @param size 文件大小，单位：byte
     * @param shorter 是否简短显示【true:小数点后1位，false:小数点后2位】
     * @return
     */
    public static String formatFileSize(long size, boolean shorter) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format(shorter ? "%.1f GB" : "%.2f GB", (float) size / gb);
        } else if (size >= mb) {
            return String.format(shorter ? "%.1f MB" : "%.2f MB", (float) size / mb);
        } else if (size >= kb) {
            return String.format(shorter ? "%.1f KB" : "%.2f KB", (float) size / kb);
        } else {
            return String.format("%d B", size);
        }
    }
    
    

    /**
     * 全角转为半角（如，转换为,）
     */
    public static String toSingleByte(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    
    
    
    
    
    
    
    public static String toString(CharSequence value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return String.valueOf(value);
    }
    public static String toString(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }


    
    /**
     * 字符串转整数
     * 
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) { }
        return defValue;
    }
    /**
     * 对象转整数
     * 
     * @param obj
     * @return 
     */
    public static int toInt(Object obj, int defValue) {
        if (obj == null)
            return defValue;
        return toInt(toString(obj), defValue);
    }

    /**
     * 对象转整数
     * 
     * @param obj
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
     * 
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
     * @param str
     * @return 
     */
    public static boolean toBoolean(String str, boolean defValue) {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static char toChar(String str, char defValue) {
        try {
            return (char) toInt(str, defValue);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static short toShort(String str, short defValue) {
        try {
            return Short.parseShort(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static float toFloat(String str, float defValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static byte toByte(String str, byte defValue) {
        try {
            return Byte.parseByte(str);
        } catch (Exception e) {
        }
        return defValue;
    }
    
}
