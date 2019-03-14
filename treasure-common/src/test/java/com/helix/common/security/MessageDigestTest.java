package com.helix.common.security;

import com.helix.common.codec.HexUtil;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.Signature;

/**
 * @author lijianyu
 * @date 2019/3/13 19:39
 */
public class MessageDigestTest {

    private static String message = "abcd";

    @Test
    public void sha256() throws Exception{
        MessageDigest sha256 = MessageDigest.getInstance("sha-256");
        sha256.update(message.getBytes("utf-8"));
        String hexStr = HexUtil.encodeHexStr(sha256.digest());
        System.out.println(hexStr);
    }

    @Test
    public void sha256with() throws Exception{
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.update(message.getBytes("utf-8"));
        signature.sign();

    }
}
