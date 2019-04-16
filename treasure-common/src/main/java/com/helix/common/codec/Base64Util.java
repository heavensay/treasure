package com.helix.common.codec;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author lijianyu
 * @date 2019/3/14 16:15
 */
public class Base64Util {

    private static String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    /**
     * 解码
     * @param encodeSrc
     * @return utf-8编码的字符串
     */
    public static String decode(String encodeSrc){
        return decode(encodeSrc,DEFAULT_CHARSET);
    }

    public static String decode(String encodeSrc,String charset){
        try {
            return new String(Base64.getDecoder().decode(encodeSrc),charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset unsupport",e);
        }
    }

    /**
     * 编码 默认使用utf-8获取src的字节码
     * @param src
     * @return
     */
    public static String encode(String src){
        return encode(src,DEFAULT_CHARSET);
    }

    public static String encode(String src,String charset){
        try {
            return Base64.getEncoder().encodeToString(src.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset unsupport",e);
        }
    }

    public static String encode(byte[] src){
        return Base64.getEncoder().encodeToString(src);
    }

    /**
     * 解码使用 URL 和文件名安全型 base64 编码方案
     * 其实就是把文件铭感的字符+和url中敏感/分别变成-和_
     * 编码 默认使用utf-8获取src的字节码
     * @param src
     * @return
     */
    public static String urlSafeEncode(String src){
        return urlSafeEncode(src,DEFAULT_CHARSET);
    }

    /**
     * 解码使用 URL 和文件名安全型 base64 编码方案
     * 其实就是把文件铭感的字符+和url中敏感/分别变成-和_
     * 编码 默认使用utf-8获取src的字节码
     * @param src
     * @return
     */
    public static String urlSafeEncode(String src, String charset){
        try {
            return Base64.getUrlEncoder().encodeToString(src.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset unsupport",e);
        }
    }

    public static String urlSafeDecode(String encodeSrc){
        return urlSafeDecode(encodeSrc,DEFAULT_CHARSET);
    }

    /**
     * 解码使用 URL 和文件名安全型 base64 编码方案
     * 其实就是把文件铭感的字符+和url中敏感/分别变成-和_
     * @param encodeSrc
     * @param charset
     * @return
     */
    public static String urlSafeDecode(String encodeSrc, String charset){
        try {
            return new String(Base64.getUrlDecoder().decode(encodeSrc),charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset unsupport",e);
        }
    }
}
