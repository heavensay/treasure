package com.helix.common;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijianyu
 * @date 2026/6/22 11:10
 */
public class EnvUtil {
    private static Map<String, String> envAttributes = new HashMap<>(System.getenv());

    static {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().filename(".env").load();
        Dotenv dotenv2 = Dotenv.configure().ignoreIfMissing().filename(".env.local").load();
        dotenv.entries().forEach(e ->
                envAttributes.put(e.getKey(), e.getValue()));

        dotenv.entries().forEach(e ->
                envAttributes.put(e.getKey(), e.getValue()));
        dotenv2.entries().forEach(e ->
                envAttributes.put(e.getKey(), e.getValue()));
        envAttributes.putAll(System.getenv());
    }

    public static String valueOf(String attributeName){
        return envAttributes.get(attributeName);
    }

    public static void main(String[] args) {
        System.out.println(valueOf("aliyun.bailian.apikey"));
        System.out.println(valueOf("aliyun.bailian.apikey2"));
    }
}
