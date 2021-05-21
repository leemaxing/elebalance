package com.liicon.utils.encrypt;

import java.security.MessageDigest;

/**
 * MD5摘要
 * @version v0.1 king 2014-10-28
 */
public class MD5Digest {

	/**
	 * 获取MD5加密后的摘要
	 * @param buffer
	 * @return
	 */
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 获取MD5加密后的数据
	 * @param buffer 原始数据
	 * @return
	 */
	public static final byte[] getRawDigest(byte[] buffer) {
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			return mdTemp.digest();
		} catch (Exception e) {
		}
		return null;
	}
}
