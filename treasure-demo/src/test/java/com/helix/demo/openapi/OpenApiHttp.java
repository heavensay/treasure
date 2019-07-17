package com.helix.demo.openapi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author ljy
 * @date 2019/7/16 11:21
 */
public class OpenApiHttp {
    private static String apiName = "baiwang.tax.monitor.query";                      //API名称
    private static String appKey = "1000482";                         //appKey
    private static String appSecret = "9d1335cb-cd9b-4dce-be6e-588f848d3678";         //appSecret
    private static String token = "99343c8f-ea44-4773-9012-85e70761bed9";             //token

    public static void main(String[] args) throws Exception
    {
        Map<String, String> textParams = new HashMap<String, String>();
        // 添加协议级请求参数
        textParams.put("method", apiName);
        textParams.put("version", "2.0");
        textParams.put("appKey", appKey);
        textParams.put("format", "json");
        textParams.put("timestamp", new Date().getTime()+"");
        textParams.put("token", token);
        textParams.put("type", "sync");

        // 2017.11.02新增，验签包括request body，API version 2.1
        String requestBody = "{\"requestId\":\"e38603b0-8a02-47be-908c-fbf2f9bc38b4\"," +
                "\"deviceType\":\"0\",\"invoiceTypeCode\":\"026\",\"machineNo\":\"499111005221\"," +
                "\"sellerTaxNo\":\"91500000747150532A\"," +
                "\"apiName\":\"baiwang.tax.monitor.query\"," +
                "\"taxNo\":\"91500000747150532A\",\"methodCode\":\"1007\"}";

        String signString = signTopRequest(textParams, appSecret, requestBody);//签名参数
        System.out.println(signString);
    }

    /**
     * 给TOP请求签名。
     *
     * @param params 所有字符型的TOP请求参数
     * @param secret 签名密钥
     * @return 签名
     * @throws Exception
     */
    public static String signTopRequest(Map<String, String> params, String secret, String body) throws Exception
    {
        // 第一步：检查参数是否已经排序
        ArrayList<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys)
        {
            String value = params.get(key);
            if (!isNull(key) && !isNull(value))
            {
                query.append(key).append(value);
            }
        }

        // 2017.11.02新增，验签包括request body，API version 2.1
        body.replaceAll("\n","");
        body.replaceAll("\t","");
        body.replaceAll("\r","");
        query.append(body);

        query.append(secret);
        // 第三步：使用MD5加密
        byte[] bytes;
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ignored)
        {
            throw new Exception(ignored);
        }

        bytes = md5.digest(query.toString().getBytes("UTF-8"));
        // 第四步：把二进制转化为大写的十六进制
        StringBuilder sign = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static boolean isNull(String str)
    {
        return (str==null || "".equals(str)?true:false);
    }
}
