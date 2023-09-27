package com.windcoder.qycms.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.windcoder.qycms.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;



public class AESUtils {
	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";
	
	/**
	 * 加密/解密算法 / 工作模式 / 填充方式
	 * 
	 * Bouncy Castle支持PKCS7Padding填充方式
	 */
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 盐值后缀，非必选
	 */
	public static final String SLATE_SUFFX = "windcoder&*-$com";

	
	/**
	 * 加密
	 * @param content
	 * @param encryptKey
	 * @return
	 */
	public static String encrypt(String content, SecretKeySpec encryptKey) {
		return encryptOrDecrypt(content, encryptKey, Cipher.ENCRYPT_MODE);
	}
	
	/**
	 * 解密
	 * @param encryptStr
	 * @param encryptKey
	 * @return
	 */
	public static String decrypt(String encryptStr, SecretKeySpec encryptKey) {
		return encryptOrDecrypt(encryptStr, encryptKey, Cipher.DECRYPT_MODE);
	}
	
	/**
	 * 生成密钥
	 * @return
	 */
	public static SecretKeySpec generateKey(String encodeRules) {
		try {
			KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			if(StringUtils.isEmpty(encodeRules)) {
				kg.init(128);
			}else {
				String newSalt = encodeRules + AESUtils.SLATE_SUFFX;
				kg.init(128,new SecureRandom(newSalt.getBytes()));
			}
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	
	private static String encryptOrDecrypt(String contentStr, SecretKeySpec encryptKey, int encryptMode) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(encryptMode, encryptKey);
			byte[] decrypted = cipher.doFinal(encryptMode==Cipher.ENCRYPT_MODE ?  contentStr.getBytes(StandardCharsets.UTF_8) : Base64.getDecoder().decode(contentStr) );
			return encryptMode==Cipher.ENCRYPT_MODE ? Base64.getEncoder().encodeToString(decrypted) : new String(decrypted, "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException(e.getMessage());
		} catch (NoSuchPaddingException e) {
			throw new BusinessException(e.getMessage());
		} catch (InvalidKeyException e) {
			throw new BusinessException(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (BadPaddingException e) {
			throw new BusinessException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
	
	public static void main(String[] agrs) {
		String salt = SecurityUtils.generateSalt();
		SecretKeySpec key = generateKey(salt+"&aa");
		System.out.println("key: "+key.getEncoded().toString());
		String content = "spark.net";
		String encryptStr = encrypt(content,key);
		System.out.println("encryptStr: "+encryptStr);
		String contentStr =  decrypt(encryptStr,key);
		System.out.println("contentStr: "+contentStr);
	}
	
	

}
