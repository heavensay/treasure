package com.helix.dict.spring.dict.bodyadvice.autoconfigure;

import com.helix.dict.EnumDictSourceAutoCollect;
import com.helix.dict.spring.dict.bodyadvice.DictMapperResponseBodyAdvice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * configuration for dict module
 * @author ljy
 * @date 2019/7/15 20:43
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "helix.enhance.dict.mapping", name = "enabled", havingValue = "true", matchIfMissing = false)
public class DictMapperAutoConfiguration {

    @Value("${helix.enhance.dict.enum.autocollect.path:#{null}}")
    private String autoCollectPath;

    @Bean
    public DictMapperResponseBodyAdvice dictMapperResponseBodyAdvice() {
        DictMapperResponseBodyAdvice dictMapperResponseBodyAdvice = new DictMapperResponseBodyAdvice();
        return dictMapperResponseBodyAdvice;
    }

    @Bean(initMethod = "enumDictCollect")
    @ConditionalOnProperty(prefix = "helix.enhance.dict.enum", name = "autocollect", havingValue = "true", matchIfMissing = true)
    public EnumDictSourceAutoCollect enumDictSourceAutoCollect() {
        EnumDictSourceAutoCollect enumCollect = new EnumDictSourceAutoCollect();

        if (autoCollectPath != null && !"".equals(autoCollectPath)) {
            enumCollect.setPackagePath(autoCollectPath);
        }else{
            //默认以main类路径，来加载枚举数据
            String packagePath = deduceMainApplicationClass().getPackage().getName();
            enumCollect.setPackagePath(packagePath);
        }
        return enumCollect;
    }


    /**
     * 获取main class
     * @return
     */
    private Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        }
        catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }
}
