package com.common.utils.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.liicon.utils.log.LLog;

import android.util.Base64;

public class RSAHelper {
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		// keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		// keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		keyBytes = Base64.decode(key.getBytes(), Base64.DEFAULT);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		/*
		 * String s = (new BASE64Encoder()).encode(keyBytes); return s;
		 */
		return new String(Base64.encode(keyBytes, Base64.DEFAULT));
	}

	public static void main(byte[] args) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 密钥位数
		keyPairGen.initialize(1024);
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		String publicKeyString = getKeyString(publicKey);
		LLog.e("public:/n" + publicKeyString);

		String privateKeyString = getKeyString(privateKey);
		LLog.e("private:/n" + privateKeyString);

		// 加解密类
		Cipher cipher = Cipher.getInstance("RSA");// Cipher.getInstance("RSA/ECB/PKCS1Padding");

		// 明文
		byte[] plainText = args;

		// 加密
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] enBytes = cipher.doFinal(plainText);

		String es = new String(Base64.encode(enBytes, Base64.DEFAULT));
		LLog.e(es);

		// 通过密钥字符串得到密钥
		publicKey = getPublicKey(publicKeyString);
		privateKey = getPrivateKey(privateKeyString);

		// 解密
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] deBytes = cipher.doFinal(enBytes);

		publicKeyString = getKeyString(publicKey);
		LLog.e("public:/n" + publicKeyString);

		privateKeyString = getKeyString(privateKey);
		LLog.e("private:/n" + privateKeyString);

		String s = new String(deBytes);
		LLog.e(s);
	}
	
}
