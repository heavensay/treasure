package com.helix.common.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author lijianyu
 * @date 2019/4/15 11:30
 */
public class UrlCodec {

    private static String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    public static class UrlEntity {
        /**
         * 基础url
         */
        public String baseUrl;
        /**
         * url参数
         */
        public Map<String, String> params = new LinkedHashMap<>();;
    }

    /**
     * 解析url
     * @param url
     * @return
     */
    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null) {
            return entity;
        }
        url = url.trim();
        if (url.equals("")) {
            return entity;
        }
        String[] urlParts = url.split("\\?");
        entity.baseUrl = urlParts[0];
        //没有参数
        if (urlParts.length == 1) {
            return entity;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            String value = keyValue.length == 2?keyValue[1]:null;
            entity.params.put(keyValue[0], value);
        }

        return entity;
    }

    /**
     * 解析完整的url为标准的urlencode编码，注意url为未urlencode的内容,i.e http://www.baidu.com?name=中国
     * @param urlStr
     * @return
     */
    public static String encodeCompleteUrl(String urlStr){
        UrlEntity urlEntity = parse(urlStr);

        List list = new ArrayList<>();
        urlEntity.params.forEach( (k,v)->{
            String paramName = encodeUrl(k);
            String paramValue = null;
            if(v == null){
                paramValue = "";
            }else{
                paramValue = encodeUrl(v);
            }

            String kv = paramName + "=" + paramValue;
            list.add(kv);
        });

        String encoingQuery = null;
        if(list.size()>0){
            encoingQuery = String.join("&",list);
        }

        if(encoingQuery!=null){
            return urlEntity.baseUrl+ "?" +encoingQuery;
        }else{
            return urlStr;
        }
    }

    /**
     * 解析content为urleconde编码格式
     * @param content
     * @return
     */
    public static String encodeUrl(String content){
        try {
            return  URLEncoder.encode(content,DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException occur",e);
        }
    }

    public static String decodeUrl(String content){
        try {
            return  URLDecoder.decode(content,DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException occur",e);
        }
    }

}
