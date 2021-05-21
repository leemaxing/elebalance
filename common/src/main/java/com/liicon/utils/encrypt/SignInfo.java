package com.liicon.utils.encrypt;

import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * APP证书签名信息
 * @version v0.1 king 2014-10-28
 */
public class SignInfo {
	
	private byte[] signatures;
	private X509Certificate certificate;
	
	private String publicKey;  // 公钥
	private String publicKeyAlgorithm;  // 公钥算法
	private String sigAlgName;  // 签名算法
	private String sigAlgOID;
	private String issuerDN;  // 发行者
	private String subjectDN;
	private String serialNumber;  // 序列号
	private Date before;  // 有效期-开始
	private Date after;  // 有效期-结束
	private int version;
	
	
	
	public SignInfo() {
		super();
	}
	public SignInfo(byte[] signatures, X509Certificate certificate,
			String publicKey, String publicKeyAlgorithm, String sigAlgName,
			String sigAlgOID, String issuerDN, String subjectDN,
			String serialNumber, Date before, Date after, int version) {
		super();
		this.signatures = signatures;
		this.certificate = certificate;
		this.publicKey = publicKey;
		this.publicKeyAlgorithm = publicKeyAlgorithm;
		this.sigAlgName = sigAlgName;
		this.sigAlgOID = sigAlgOID;
		this.issuerDN = issuerDN;
		this.subjectDN = subjectDN;
		this.serialNumber = serialNumber;
		this.before = before;
		this.after = after;
		this.version = version;
	}
	
	
	
	/**
	 * 解析证书信息
	 * @param certificate
	 */
	void parse(X509Certificate certificate) {
		// 解析证书信息
        String publicKey = certificate.getPublicKey().toString();
        try {
        	publicKey = publicKey.substring(publicKey.indexOf("modulus") + 8, publicKey.indexOf(","));
		} catch (Exception e) { }
        
        
		this.publicKey = publicKey;
		this.publicKeyAlgorithm = certificate.getPublicKey().getAlgorithm();
		this.sigAlgName = certificate.getSigAlgName();
		this.sigAlgOID = certificate.getSigAlgOID();
		this.issuerDN = certificate.getIssuerX500Principal().getName();
		this.subjectDN = certificate.getSubjectX500Principal().getName();
		this.serialNumber = certificate.getSerialNumber().toString();
		this.before = certificate.getNotAfter();
		this.after = certificate.getNotAfter();
		this.version = certificate.getVersion();
	}
	


	@Override
	public String toString() {
		return "SignInfo [signatures=" + signatures
				+ ", certificate=" + (certificate==null ? "null" : certificate.getClass())
				+ ", publicKey=" + publicKey
				+ ", publicKeyAlgorithm=" + publicKeyAlgorithm
				+ ", sigAlgName=" + sigAlgName + ", sigAlgOID=" + sigAlgOID
				+ ", issuerDN=" + issuerDN + ", subjectDN=" + subjectDN
				+ ", serialNumber=" + serialNumber + ", before=" + before
				+ ", after=" + after + ", version=" + version + "]";
	}
	

	
	public X509Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	public byte[] getSignatures() {
		return signatures;
	}
	public void setSignatures(byte[] signatures) {
		this.signatures = signatures;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getSigAlgName() {
		return sigAlgName;
	}
	public void setSigAlgName(String sigAlgName) {
		this.sigAlgName = sigAlgName;
	}
	public String getSigAlgOID() {
		return sigAlgOID;
	}
	public void setSigAlgOID(String sigAlgOID) {
		this.sigAlgOID = sigAlgOID;
	}
	public String getIssuerDN() {
		return issuerDN;
	}
	public void setIssuerDN(String issuerDN) {
		this.issuerDN = issuerDN;
	}
	public String getSubjectDN() {
		return subjectDN;
	}
	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getPublicKeyAlgorithm() {
		return publicKeyAlgorithm;
	}
	public void setPublicKeyAlgorithm(String publicKeyAlgorithm) {
		this.publicKeyAlgorithm = publicKeyAlgorithm;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Date getBefore() {
		return before;
	}
	public void setBefore(Date before) {
		this.before = before;
	}
	public Date getAfter() {
		return after;
	}
	public void setAfter(Date after) {
		this.after = after;
	}
}
