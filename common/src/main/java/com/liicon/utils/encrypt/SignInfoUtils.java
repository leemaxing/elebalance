package com.liicon.utils.encrypt;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import com.liicon.utils.log.LLog;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

/**
 * APP证书签名信息-工具类
 * @version v0.1 king 2014-10-28
 */
public class SignInfoUtils {
	
	/**
	 * 获取签名相关信息
	 * @param context 上下文
	 * @param packageName 要获取签名信息的包
	 * @return
	 */
	public static SignInfo getSignInfo(Context context, String packageName) {
		// 获取证书对象
		Signature signatures = SignInfoUtils.getSignature(context, packageName);
		if (signatures == null) {
			return null;
		}
		
		X509Certificate certificate = SignInfoUtils.getCertificate(signatures);
		if (certificate == null) {
			return null;
		}
		
		// 创建证书信息实体类
		SignInfo signInfo = new SignInfo();
		signInfo.setSignatures(signatures.toByteArray());
		signInfo.setCertificate(certificate);
		
		// 解析证书信息
		signInfo.parse(certificate);
		
		return signInfo;
	}
	
	
	/**
	 * 获取签名摘要(MD5加密)
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getSignDigest(Context context, String packageName) {
		Signature signature = getSignature(context, packageName);
		return MD5Digest.getMessageDigest(signature.toByteArray());
	}
	
	
	/**
	 * 检查签名的有效性（根据公钥）
	 * <p>
	 * 检查指定包名的APP，签名是否为给定的签名
	 * </p>
	 * @param context
	 * @param packageName 包名
	 * @param publicKey 公钥
	 * @return ture:有效
	 */
	public static boolean checkSignByPublicKey(Context context, String packageName, String publicKey) {
		if (TextUtils.isEmpty(publicKey)) {
			throw new NullPointerException("'PublicKey' cannot be empty.");
		}
		
		SignInfo signInfo = getSignInfo(context, packageName);
		if (publicKey.equals(signInfo.getPublicKey())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查签名的有效性（根据公钥）
	 * <p>
	 * 检查指定包名的APP，签名是否为给定的签名
	 * </p>
	 * @param context
	 * @param packageName 包名
	 * @param digest 签名的MD5摘要
	 * @return ture:有效
	 */
	public static boolean checkSignByDigest(Context context, String packageName, String digest) {
		if (TextUtils.isEmpty(digest)) {
			throw new NullPointerException("'Digest' cannot be empty.");
		}
		
		if (digest.equals(getSignDigest(context, packageName))) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	

	/**
	 * 获取签名
	 */
	static Signature getSignature(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return null;
		}
		
		PackageManager pkManager = context.getPackageManager();
		try {
			PackageInfo pkInfo = pkManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			if (pkInfo != null) {
				Signature[] signatures = pkInfo.signatures;
				if ((signatures == null) || (signatures.length == 0)) {
					return null;
				}
				return signatures[0];
			}
			return null;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			return null;
		}
	}
	
	/**
	 * 获取证书
	 * @param signature
	 * @return
	 */
	static X509Certificate getCertificate(Signature signature) {
		if (signature == null) {
			return null;
		}
		
		try {
			CertificateFactory certFactory = CertificateFactory
					.getInstance("X.509");
			X509Certificate cert = (X509Certificate) certFactory
					.generateCertificate(new ByteArrayInputStream(signature.toByteArray()));
			return cert;
		} catch (CertificateException e) {
			LLog.e(e);
		} catch (Exception e) {
            LLog.e(e);
		}
		return null;
	}

	/**
	 * 获取证书
	 */
	static X509Certificate getCertificate(Context context, String packageName) {
		return getCertificate(getSignature(context, packageName));
	}
	

	/**
	 * 获取公钥
	 * @param certificate
	 * @return
	 */
	static String getPublicKey(Certificate certificate) {
        String publickey = certificate.getPublicKey().toString();
        try {
            publickey = publickey.substring(publickey.indexOf("modulus") + 8, publickey.indexOf(","));
		} catch (Exception e) {
            LLog.e(e);
		}
        return publickey;
    }
	
}
