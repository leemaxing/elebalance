package com.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

/**
 * DES/CBC/PKCS5Padding模式的加解密
 * 
 * <p>
 * Potentially insecure random numbers on Android 4.3 and older.
 * Read https://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html for more info.
 * </p>
 * 
 * @version v0.1 king 2014-11-7 DES/CBC/PKCS5Padding模式
 */
public class DesCbcPkcs5padding {

	public static String encrypt(String data, String key) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "DES");
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] encrypted = cipher.doFinal(data.getBytes());
			return Base64.encodeToString(encrypted, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String desEncrypt(String data, String key) throws Exception {
		try {
			byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "DES");
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
