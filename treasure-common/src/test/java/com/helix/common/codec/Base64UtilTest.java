package com.helix.common.codec;

import org.junit.Assert;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.Base64;

/**
 * @author lijianyu
 * @date 2019/3/14 16:36
 */
public class Base64UtilTest {

    private static String str = "中国abc123";
//    private static String urlStr = "/book/query?name=中国abc123+/";
    private static String urlStr = "中国";

    @Test
    public void encodeAndDecode() throws Exception{
        System.out.println(Base64Util.encode(str));

        String origStr = Base64Util.decode(Base64Util.encode(str));

        System.out.println(origStr);
        Assert.assertEquals(str,origStr);
    }

    @Test
    public void urlEcodeAndDecode() throws Exception{
        String urlStr = "http://www.baidu.com?name=中国";
        System.out.println(Base64Util.urlSafeEncode(urlStr));;

        String origStr = Base64Util.urlSafeDecode(Base64Util.urlSafeEncode(urlStr));

        System.out.println(origStr);
        System.out.println(URLEncoder.encode(urlStr,"utf-8"));;
        System.out.println(Base64Util.decode(URLEncoder.encode(urlStr,"utf-8")));

        Assert.assertEquals(urlStr,origStr);

    }

    @Test
    public void encodeJdk() throws Exception{
        System.out.println(Base64.getEncoder().encodeToString(str.getBytes("utf-8")));
        System.out.println(new String(Base64.getEncoder().encode(str.getBytes("utf-8"))));
        System.out.println(new String(Base64.getEncoder().encode(str.getBytes("utf-8")),"gbk"));
        System.out.println(new String(Base64.getEncoder().encode(str.getBytes("utf-8")),"utf-8"));
        System.out.println(new String(Base64.getEncoder().encode(str.getBytes("utf-8")),"ascii"));
    }

    @Test
    public void decodeJdk()throws Exception{
        String encodeStr = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));

        System.out.println(new String(Base64.getDecoder().decode(encodeStr)));
        System.out.println(new String(Base64.getDecoder().decode(encodeStr),"utf-8"));
        System.out.println(new String(Base64.getDecoder().decode(encodeStr),"gbk"));
    }

}