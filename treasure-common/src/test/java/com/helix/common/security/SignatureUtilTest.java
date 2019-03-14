package com.helix.common.security;

import com.helix.common.codec.HexUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 签名算法测试类
 * Signature 类用来为应用程序提供数字签名算法功能。数字签名用于确保数字数据的验证和完整性。
 * <p>
 * 1初始化，使用
 * 初始化验证签名的公钥（请参见 initVerify），或使用
 * 初始化签署签名的私钥（也可以选择“安全随机数生成器”），（请参见 initSign(PrivateKey) 和 initSign(PrivateKey, SecureRandom)）。
 * <p>
 * 2更新 根据初始化类型，这可更新要签名或验证的字节。请参见 update 方法。
 * <p>
 * 3 签署或验证所有更新字节的签名。请参见 sign 方法和 verify 方法。
 *
 * @author lijianyu
 * @date 2019/3/13 19:55
 */
public class SignatureUtilTest {

    @Test
    public void signAndVerify()throws Exception{
        //1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String plainText = "abcd";
        byte[] ciphers = SignatureUtil.sign(keyPair.getPrivate(),plainText);

        boolean match = SignatureUtil.verifySign(keyPair.getPublic(),plainText,ciphers);
        Assert.assertTrue(match);
    }

    @Test
    public void signAndVerifyBySha1withRSA()throws Exception{
        //1.初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String plainText = "abcd";
        byte[] ciphers = SignatureUtil.sign(keyPair.getPrivate(),plainText,SignatureUtil.ALGORITHM_SHA1WITHRSA);

        boolean match = SignatureUtil.verifySign(keyPair.getPublic(),plainText,ciphers,SignatureUtil.ALGORITHM_SHA1WITHRSA);
        Assert.assertTrue(match);
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
