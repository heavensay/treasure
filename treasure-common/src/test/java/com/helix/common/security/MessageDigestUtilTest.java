package com.helix.common.security;

import com.helix.common.codec.HexUtil;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.Signature;
import java.util.Base64;

/**
 * @author lijianyu
 * @date 2019/3/13 19:39
 */
public class MessageDigestUtilTest {

    private static String message = "abcd";

    @Test
    public void sha256() throws Exception{
        MessageDigest sha256 = MessageDigest.getInstance("sha-256");
        sha256.update(message.getBytes("utf-8"));
        String hexStr = HexUtil.encodeHexStr(sha256.digest());
        String hexStr2 = new String(sha256.digest());
        System.out.println(hexStr);
        System.out.println(hexStr2);
    }

    @Test
    public void sha256with() throws Exception{
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.update(message.getBytes("utf-8"));
        signature.sign();
    }

    @Test
    public void md5() throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("md5");
        md5.update(message.getBytes("utf-8"));
        byte[] byteMd5 = md5.digest();

        String hexStr = HexUtil.encodeHexStr(byteMd5);
        String str = new String(byteMd5);
        String base64 = Base64.getEncoder().encodeToString(byteMd5);
        System.out.println(hexStr);
        System.out.println(str);
        System.out.println(base64);
    }

    /**
     * 测试update和digest中传入参数区别；
     * md5.update(byte1),md5.digest(byte2) == md5.digest(byte1+byte2)
     * @throws Exception
     */
    @Test
    public void md5ForUpdateAndDigest() throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("md5");

        md5.reset();
        md5.update("123".getBytes("utf-8"));
        System.out.println(HexUtil.encodeHexStr(md5.digest("abc".getBytes("utf-8"))));

        md5.reset();
        System.out.println(HexUtil.encodeHexStr(md5.digest("123abc".getBytes("utf-8"))));
    }

    @Test
    public void md5Encrypt(){
        System.out.println(MessageDigestUtil.md5HexStr("abc"));
        System.out.println(MessageDigestUtil.md5Base64("abc"));
    }

}
