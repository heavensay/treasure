package com.helix.common.security;

import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * 数字签名算法工具类
 *
 * Signature 类用来为应用程序提供数字签名算法功能。数字签名用于确保数字数据的验证和完整性
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
 * @date 2019/3/7 16:54
 */
public class SignatureUtil {

    public static final String DEFAULT_SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static final String ALGORITHM_SHA1WITHRSA = "SHA1withRSA";
    public static final String ALGORITHM_SHA256WITHRSA = "SHA256withRSA";
    public static final String ALGORITHM_SHA512WITHRSA = "SHA512withRSA";
    public static final String ALGORITHM_MD5WITHRSA = "MD5withRSA";

    public static final String DEFAULT_CHARSET = "utf-8";



    public static byte[] sign(PrivateKey privateKey, String plainText){
        return sign(privateKey, plainText, DEFAULT_CHARSET, DEFAULT_SIGNATURE_ALGORITHM);
    }

    public static byte[] sign(PrivateKey privateKey, String plainText,String algorithm){
        return sign(privateKey, plainText, DEFAULT_CHARSET,algorithm);
    }

    public static byte[] sign(PrivateKey privateKey, String plainText,String charset,String algorithm){
        try {
            byte[] plainBytes = plainText.getBytes(charset);
            return signWithSecurityExcpeiton(privateKey,plainBytes, algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset not support",e);
        }
    }

    /**
     * 签名
     *
     * @param privateKey
     *            私钥
     * @param plainBytes
     *            明文
     * @return
     */
    public static byte[] sign(PrivateKey privateKey, byte[] plainBytes,String algorithm) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        Signature Sign = Signature.getInstance(algorithm);
        Sign.initSign(privateKey);
        Sign.update(plainBytes);
        return Sign.sign();
    }

    /**
     * 签名
     *
     * @param privateKey
     *            私钥
     * @param plainBytes
     *            明文
     * @return
     */
    public static byte[] signWithSecurityExcpeiton(PrivateKey privateKey, byte[] plainBytes,String algorithm){
        byte[] result = null;
        try {
            Signature Sign = null;
            Sign = Signature.getInstance(algorithm);
            Sign.initSign(privateKey);
            Sign.update(plainBytes);
            result = Sign.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new SecurityException(e);
        }
        return result;
    }


    /**
     * 验签
     *
     * @param publicKey
     *            公钥
     * @param plainBytes
     *            明文
     * @param signed
     *            签名
     */
    public static boolean verifySign(PublicKey publicKey, byte[] plainBytes, byte[] signed,String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature verifySign = Signature.getInstance(algorithm);
        verifySign.initVerify(publicKey);
        verifySign.update(plainBytes);
        return verifySign.verify(signed);
    }

    /**
     * 验签
     *
     * @param publicKey
     *            公钥
     * @param plainBytes
     *            明文
     * @param signed
     *            签名
     */
    public static boolean verifySignWithSecurityException(PublicKey publicKey, byte[] plainBytes, byte[] signed,String algorithm) {
        try {
            Signature verifySign = Signature.getInstance(algorithm);
            verifySign.initVerify(publicKey);
            verifySign.update(plainBytes);
            return verifySign.verify(signed);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new SecurityException(e);
        }
    }


    public static boolean verifySign(PublicKey publicKey, String plainText,byte[] signed, String charset,String algorithm) {
        try {
            byte[] plainBytes = plainText.getBytes("utf-8");
           return verifySignWithSecurityException(publicKey,plainBytes,signed,algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset not support",e);
        }
    }

    public static boolean verifySign(PublicKey publicKey, String plainText,byte[] signed,String algorithm) {
        return verifySign(publicKey, plainText, signed, DEFAULT_CHARSET,algorithm);
    }

    public static boolean verifySign(PublicKey publicKey, String plainText,byte[] signed) {
        return verifySign(publicKey, plainText, signed, DEFAULT_SIGNATURE_ALGORITHM);
    }
}
