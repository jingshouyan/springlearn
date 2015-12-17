package com.jing.web.util.rsa;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class RSA {
    public static String sign(String data, String privateKey)
    {
        Signature signature;
        try
        {
            signature = Signature.getInstance("MD5withRSA");
            signature.initSign(loadPrivateKey(privateKey));
            signature.update(data.getBytes("UTF-8"));
            byte[] bytes = signature.sign();
            String sign = Base64.encodeBase64String(bytes);
            return sign;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static boolean verify(String data, String sign, String publicKey)
    {
        try
        {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(loadPublicKey(publicKey));
            signature.update(data.getBytes("UTF-8"));
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

    private static PublicKey loadPublicKey(String keyBody) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException
    {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(keyBody);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey loadPrivateKey(String keyBody)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(keyBody);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
