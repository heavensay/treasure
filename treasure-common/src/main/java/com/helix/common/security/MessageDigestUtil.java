package com.helix.common.security;

import com.helix.common.codec.Base64Util;
import com.helix.common.codec.HexUtil;

import java.security.MessageDigest;

/**
 * 消息摘要工具类md5，sha
 * @author ljy
 * @date 2019/4/16 14:02
 */
public class MessageDigestUtil {
    public static String md5HexStr(String srcStr){
        return calcDigest("MD5", srcStr);
    }

    public static String md5Base64(String srcStr){
        return calcDigest("MD5", srcStr,false);
    }


    public static String sha1HexStr(String srcStr){
        return calcDigest("SHA-1", srcStr);
    }

    public static String sha256HexStr(String srcStr){
        return calcDigest("SHA-256", srcStr);
    }

    public static String sha384HexStr(String srcStr){
        return calcDigest("SHA-384", srcStr);
    }

    public static String sha512HexStr(String srcStr){
        return calcDigest("SHA-512", srcStr);
    }

    /**
     * @param algorithm
     * @param srcStr
     * @return
     */
    public static String calcDigest(String algorithm, String srcStr) {
        return calcDigest(algorithm, srcStr,true);
    }

    /**
     * @param algorithm
     * @param srcStr
     * @param isConvertHex ture：结果以hex表示，false：以base64表示；
     * @return
     */
    public static String calcDigest(String algorithm, String srcStr,boolean isConvertHex) {
        try {
            StringBuilder result = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            if(isConvertHex){
                return HexUtil.encodeHexStr(bytes);
            }else{
                return Base64Util.encode(bytes);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
