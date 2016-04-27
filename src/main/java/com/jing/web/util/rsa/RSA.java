package com.jing.web.util.rsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.springframework.util.Base64Utils;

import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class RSA {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
     
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA"; 
     
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
     
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    
    /**
     * 默认字符集
     */
    private static final String CHARSET="UTF-8";
    
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
     
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
 
    
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
	
    /**
     * 
     * sign:私钥签名. <br/>
     *
     * @author bxy-jing
     * @param data
     * @param privateKey
     * @return
     * @since JDK 1.6
     */
    public static String sign(String data, String privateKey)
    {
        Signature signature;
        try
        {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(loadPrivateKey(privateKey));
            signature.update(data.getBytes(CHARSET));
            byte[] bytes = signature.sign();
            String sign = Base64Utils.encodeToString(bytes);
            return sign;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 
     * verify:公钥验签. <br/>
     *
     * @author bxy-jing
     * @param data
     * @param sign
     * @param publicKey
     * @return
     * @since JDK 1.6
     */
    public static boolean verify(String data, String sign, String publicKey)
    {
        try
        {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(loadPublicKey(publicKey));
            signature.update(data.getBytes(CHARSET));
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] bytes = base64Decoder.decodeBuffer(sign);
            return signature.verify(bytes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * <P>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String encryptedStr, String privateKey)
            throws Exception {
    	byte[] encryptedData = Base64Utils.decodeFromString(encryptedStr);
        Key privateK = loadPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData,CHARSET);
    }
 
    /**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(String encryptedstr, String publicKey)
            throws Exception {
        Key publicK = loadPublicKey(publicKey);
        byte[] encryptedData = Base64Utils.decodeFromString(encryptedstr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param str 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String str, String publicKey)
            throws Exception {
        Key publicK = loadPublicKey(publicKey);
        byte[] data = str.getBytes(CHARSET);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Utils.encodeToString(encryptedData);
    }
 
    /**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param str 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String str, String privateKey)
            throws Exception {
        Key privateK = loadPrivateKey(privateKey);
        byte[] data = str.getBytes(CHARSET);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Utils.encodeToString(encryptedData);
    }
 


    private static PublicKey loadPublicKey(String keyBody) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException
    {
    	byte[] bytes = Base64Utils.decodeFromString(keyBody);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey loadPrivateKey(String keyBody)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        byte[] bytes = Base64Utils.decodeFromString(keyBody);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
    
    public static void main(String[] args) {
    	try {
			Map<String,Object> map = genKeyPair();
			String pub = Base64Utils.encodeToString(((Key)map.get(PUBLIC_KEY)).getEncoded());
			String pri = Base64Utils.encodeToString(((Key)map.get(PRIVATE_KEY)).getEncoded());
			System.out.println(pub);
			System.out.println(pri);
			String str = "靖守彦呵呵哒！@#aasdf我耳朵色弱威尔卡上来付款了吗，阿萨德忙否文件，为未来可如今、我耳机去看我家二楼去k.moiwerj。电视服务就饿哦日。奥斯的覅杰威尔据了解萨拉反馈奇偶位软件数量的房间诶健康，为人生的风景，我好威尔卡是否可凉快\n";
//			for(int i=0;i<10;i++){
//				str=str+str;
//			}
			String str2 = sign(str, pri);
			System.out.println(str2);
			boolean b = verify(str, str2, pub);
			System.out.println(b);
			String str3 = encryptByPublicKey(str, pub);
			System.out.println(str3);
			String str4 = decryptByPrivateKey(str3, pri);
			System.out.println(str4);
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
}
