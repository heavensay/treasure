package com.helix.demo.lang.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ljy
 * @date 2019/6/12 9:29
 */
public class InputStreamTest {

    /**
     * InputStreamReader读取string
     * @throws Exception
     */
    @Test
    public void inputStreamReader() throws Exception{
        String str = "中国abc123";
        InputStream is = new ByteArrayInputStream(str.getBytes("utf-8"));

        InputStreamReader isr = new InputStreamReader(is,"utf-8");

        int num = 0;
        int size = 3;
        char[] cs = new char[size];
        StringBuilder builder = new StringBuilder();
        while ((num = isr.read(cs,0,size)) != -1){
//            count = count + num;
            builder.append(cs,0,num);
            System.out.println(builder.toString());
        }
        System.out.println(builder.toString());
    }

    /**
     * InputStreamReader读取string
     * @throws Exception
     */
    @Test
    public void inputStreamReader2() throws Exception{
        String str = "中国abc123";
        InputStream is = new ByteArrayInputStream(str.getBytes("utf-8"));

        InputStreamReader isr = new InputStreamReader(is,"utf-8");

        int num = 0;
        char[] cs = new char[3];
        StringBuilder builder = new StringBuilder();
        while ((num = isr.read(cs)) != -1){
            System.out.println("num:"+num+",char:"+new String(cs,0,num));
            builder.append(cs,0,num);
        }
        System.out.println(builder.toString());
    }
}
