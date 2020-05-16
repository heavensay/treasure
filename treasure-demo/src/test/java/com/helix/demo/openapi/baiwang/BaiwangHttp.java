package com.helix.demo.openapi.baiwang;

import com.alibaba.fastjson.JSON;
import com.helix.demo.openapi.alibaba.OpenApiHttp;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ljy
 * @date 2019/7/16 11:21
 */
public class BaiwangHttp {
    private static String url = "https://sandbox.yinshuitong.com/api";
    private static String appKey = "1000482";                         //appKey
    private static String appSecret = "9d1335cb-cd9b-4dce-be6e-588f848d3678";         //appSecret

    public static void assetVerfiy(AssetVerifyParam param){
        String body = JSON.toJSONString(param);
        String apiName = "winLending.finance.assets.invoiceCheck";
        try {

            Map commonParams = buildCommonParam(apiName);
            String sign = OpenApiHttp.signTopRequest(commonParams,appSecret,body);
            commonParams.put("sign",sign);
//            HttpUtil.post()

            String requestParamString = map2Strng(commonParams);



            HttpPost httpPost = new HttpPost(url+"?"+requestParamString);

//            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
//            for (Map.Entry<String, Object> param : headers.entrySet()) {
//                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
//            }


            StringEntity entity2 = new StringEntity(body, StandardCharsets.UTF_8);
            httpPost.setEntity(entity2);

            String result = null;

            try {
                HttpClient httpClient = HttpClientBuilder.create().build();

                CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    /**
                     * 智能编码获取网页内容
                     * 1优先使用头信息header content-type
                     * 2使用html内容中mta标签编码，meta:content-type(<meta http-equiv=content-type content=text/html;charset=utf-8>)
                     */
                    final ContentType contentType = ContentType.get(entity);
                    if (contentType != null && contentType.getCharset() != null) {
                        result = EntityUtils.toString(entity);
                    } else {
                        byte[] bytes = EntityUtils.toByteArray(entity);
                        if (bytes != null && bytes.length > 0) {
                            String metaCharset = findChartsetByBody(bytes);
                            result = new String(bytes, metaCharset != null ? metaCharset : "utf-8");
                        }
                    }

//				/**
//				 * 1优先使用header:content-type.charset来编码返回的html
//				 * 2默认使用DEFAULT_CHAR_SET来编码返回的html
//				 */
//                result = EntityUtils.toString(entity,DEFAULT_CHAR_SET);

                    EntityUtils.consume(entity);
                }

            } catch (IOException e) {
                System.out.println(e);
            }
            System.out.println("result:==========="+result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String findChartsetByBody(byte[] htmlBytes) {
        String html = new String(htmlBytes);// 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
        String charset = null;
        String regEx = "<meta.*?charset=([[a-z]|[A-Z]|[0-9]|-]*)>";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        System.out.println(html);
        Matcher m = p.matcher(html);
        if (m.groupCount() > 0) {
            m.find();
            charset = m.group(1);
        }
        return charset;
    }

    private static Map buildCommonParam(String apiName){
        Map map = new HashMap();
        map.put("method",apiName);
        map.put("version","3.0");
        map.put("appKey",appKey);
        map.put("format","json");
        map.put("timestamp",new Date().getTime()+"");
        map.put("type","sync");
        map.put("token",getToken());
//        map.put("sign",sign);
        return map;
    }


    private static String getToken(){
        return "d876b7d5-b3e3-46ed-ae47-a0648e8d9aac";
    }

    private static String map2Strng(Map map){
        StringBuilder builder = new StringBuilder("");
        map.forEach((k,v)->{
            builder.append(k).append("=").append(Optional.ofNullable(v).orElse("")+"&");
        });
        return builder.substring(0,builder.length()-1);
    }
}
