package com.helix.dict.spring.bodyadvice;

import com.helix.dict.SysDictManager;
import com.helix.dict.introspect.DictBeanIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 针对Controller返回值，进行字典自动转换
 * @author ljy
 * @date 2019/1/28 21:34
 */
@RestControllerAdvice
//@DependsOn("globalResponseHandler")
public class DictMapperResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    Logger logger = LoggerFactory.getLogger(DictMapperResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return DictBeanIntrospector.acquireDictMetadata(returnType.getParameterType()).size()>0;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        SysDictManager.mapping(body);
        return body;
    }
}