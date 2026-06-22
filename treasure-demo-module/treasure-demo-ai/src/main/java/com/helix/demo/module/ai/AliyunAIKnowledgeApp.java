package com.helix.demo.module.ai;

import com.alibaba.dashscope.app.*;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.helix.common.EnvUtil;

/**
 * @author lijianyu
 * @date 2026/6/20 17:39
 *
 * 阿里云百炼平台ai能力接入
 */
public class AliyunAIKnowledgeApp {
    public static void appCall()
            throws ApiException, NoApiKeyException, InputRequiredException {
        ApplicationParam param = ApplicationParam.builder()
                // 若没有配置环境变量，可用百炼API Key将下行替换为：.apiKey("sk-xxx")。但不建议在生产环境中直接将API Key硬编码到代码中，以减少API Key泄露风险。
//                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .apiKey(EnvUtil.valueOf("aliyun.bailian.apikey"))
                .appId(EnvUtil.valueOf("aliyun.bailian.knowledge.agent.appid"))
                .prompt("个人blog仓库介绍")
                .build();

        Application application = new Application();
        System.out.println("内容生成中请耐心等待...");
        ApplicationResult result = application.call(param);
        System.out.printf("text: %s\n",
                result.getOutput().getText());
    }

    public static void main(String[] args) {
        try {
            appCall();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("message："+e.getMessage());
            System.out.println("请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code");
        }
        System.exit(0);
    }
}
