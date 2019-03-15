package com.helix.common.security;

import com.helix.common.codec.HexUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author lijianyu
 * @date 2019/3/15 17:38
 */
public class RSATest {

    @Test
    public void rsaEncrypt()throws Exception{
        String str = "abcd";
        KeyPair keyPair = generateRsaKeyPair();

        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] encryptBytes = cipher.doFinal(str.getBytes("utf-8"));
        String hex1 = HexUtil.encodeHexStr(encryptBytes);
        System.out.println("机密内容：" + hex1);

        KeyPair keyPair2 = generateRsaKeyPair();
        cipher.init(Cipher.DECRYPT_MODE,keyPair2.getPublic());
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        System.out.println(new String(decryptBytes,"utf-8"));
    }

    @Test
    public void rsaSign()throws Exception{
        String str = "abcd";
        KeyPair keyPair = generateRsaKeyPair();

        //RSA签名
        byte[] keyBytes = keyPair.getPrivate().getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(str.getBytes());
        byte[] signBytes = signature.sign();
        System.out.println("加密内容：" + HexUtil.encodeHexStr(signBytes));

        byte[] keyBytes2 = keyPair.getPublic().getEncoded();
        X509EncodedKeySpec keySpec2 = new X509EncodedKeySpec(keyBytes2);
        KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
        PublicKey key2 = keyFactory.generatePublic(keySpec2);
        Signature signature2 = Signature.getInstance("MD5withRSA");
        signature2.initVerify(key2);
        signature2.update(str.getBytes());
//        System.out.println(new String(decryptBytes,"utf-8"));
        signature2.verify(signBytes);
    }

    private KeyPair generateRsaKeyPair()throws Exception{
        //1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return keyPair;
    }

    /**
     * sha256withrsa
     * @throws Exception
     */
    @Test
    public void testWithSha256withRSA()throws Exception{
        //待加密字符串
        String str = "abcd";

        //生成rsa秘钥对
        KeyPair keyPair  = generateRsaKeyPair();

        //使用sha256withrsa算法，对str进行sha256withrsa的只要信息，使用私钥进行加密；获取签名摘要信息
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(str.getBytes("utf-8"));
        byte[] result = signature.sign();
        String hex1 = HexUtil.encodeHexStr(result);
        System.out.println("sha246withrsa签名串：\n" + hex1);


        MessageDigest sha256 = MessageDigest.getInstance("sha-256");
        sha256.update(str.getBytes("utf-8"));
        byte[] sha256Bytes = sha256.digest();
        String hexStr = HexUtil.encodeHexStr(sha256.digest());

        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        String hex2 = HexUtil.encodeHexStr(cipher.doFinal(sha256Bytes));
        System.out.println("sha256散列值rsa加密:\n" + hex2);

        Assert.assertEquals(hex1,hex2);//output:fail
    }
}
