package com.demo.fruitmarket.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        System.out.println("body = " + body + ", methodParameter = " + methodParameter + ", mediaType = " + mediaType + ", aClass = " + aClass + ", serverHttpRequest = " + serverHttpRequest + ", serverHttpResponse = " + serverHttpResponse);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            //TODO 整理一下回傳代碼
            String result = objectMapper.writeValueAsString(body);
            System.out.println("result = " + result);
            return new CommonResult("9487", "預設回傳", body);
        } catch (JsonProcessingException e) {
            return "不預期的意外！";
        }

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(FruitMarketException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
