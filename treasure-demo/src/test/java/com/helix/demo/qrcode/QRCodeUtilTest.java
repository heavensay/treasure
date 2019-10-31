package com.helix.demo.qrcode;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class QRCodeUtilTest {

    @Test
    public void encode() throws Exception{
        String content = "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542";
        QRCodeUtil.encode(content,"/Users/liyu/Desktop/work/租到啦/qrcode/333.jpeg");
    }

    @Test
    public void decode() throws Exception{
        String str = QRCodeUtil.decode("/Users/liyu/Desktop/work/租到啦/qrcode/333/88966962.jpg");
        System.out.println(str);
    }

    @Test
    public void encode2() throws Exception{
        String content = "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        QRCodeUtil.encode(content,os);
        System.out.println(os.toByteArray().length);
    }
}
