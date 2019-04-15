package com.helix.common.codec;

import org.junit.Test;

import java.net.URL;

/**
 * @author ljy
 * @date 2019/4/15 11:37
 */
public class UrlCodecTest {

    private static String URL_STR = "http://www.baidu.com?name=李明&age=18";

    @Test
    public void urlCompleteEncoding() throws Exception{
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?name"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?name="));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?name=1"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?name=中国"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?中国=zh"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?name=中国&age=18"));
        System.out.println(UrlCodec.encodeCompleteUrl("http://www.123.com?name=中国&age="));
        System.out.println(UrlCodec.encodeCompleteUrl("name=中国&age="));
    }

    @Test
    public void url() throws Exception{
        URL url = new URL(URL_STR);
        int port = url.getPort();
        String host = url.getHost();
        String uri_path = url.getPath();
        String request_file = url.getFile();
        String query = url.getQuery();
        System.out.println("url: "+ url);
        System.out.println("host: "+ host);
        System.out.println("port: "+ port);
        System.out.println("uri_path: "+ uri_path);
        System.out.println("request_file: "+ request_file);
        System.out.println("query: "+ query);
    }

    @Test
    public void join(){
        String[] strs = new String[]{"a","b"};
        System.out.println(String.join("&",strs));;
    }
}