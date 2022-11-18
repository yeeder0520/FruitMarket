package com.demo.fruitmarket.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 統一回傳格式.
 * 2021/4/23 下午 03:15
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@ControllerAdvice
public class ResponseEntityConfig implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            final MethodParameter methodParameter,
            final Class<? extends HttpMessageConverter<?>> aClass
    ) {
        return true;
    }

    /**
     * @param body               實際回傳值
     * @param methodParameter    methodParameter
     * @param mediaType          mediaType
     * @param aClass             消息轉換器
     * @param serverHttpRequest  serverHttpRequest
     * @param serverHttpResponse serverHttpResponse
     * @return Object
     */
    @Override
    public Object beforeBodyWrite(
            final Object body,
            final MethodParameter methodParameter,
            final MediaType mediaType,
            final Class<? extends HttpMessageConverter<?>> aClass,
            final ServerHttpRequest serverHttpRequest,
            final ServerHttpResponse serverHttpResponse
    ) {
//        if (body instanceof String) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                return objectMapper.writeValueAsString(new CommonResult<>(200, "", body));
//            } catch (JsonProcessingException e) {
//                return "不預期的意外！";
//            }
//        }
        return new CommonResult("9487", "預設回傳", body);
    }

    @ExceptionHandler(value = FruitMarketException.class)
    public CommonResult handleException(FruitMarketException e) {
        return CommonResult.fail(e);
    }
}
