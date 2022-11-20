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
import org.springframework.validation.BindException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 統一回傳格式.
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@ControllerAdvice(annotations = RestController.class)
public class ResponseEntityConfig implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            final MethodParameter methodParameter,
            final Class<? extends HttpMessageConverter<?>> aClass
    ) {
        //回傳資料型態為CommonResult才攔截
        return methodParameter.getParameterType().isAssignableFrom(CommonResult.class);
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
        if (body instanceof CommonResult) {
            return body;
        }
        if (body instanceof String) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(body);
            } catch (JsonProcessingException e) {
                return "不預期意外";
            }
        }
        return CommonResult.success(body);
    }

    /**
     * 處理水果市場的異常
     *
     * @param e FruitMarketException
     * @return CommonResult
     */
    @ExceptionHandler(FruitMarketException.class)
    public ResponseEntity<CommonResult<FruitMarketException>> fruitMarketExceptionHandle(FruitMarketException e) {
        return ResponseEntity.badRequest().body(CommonResult.fail(e));
    }

    /**
     * 處理 Validation
     *
     * @param bindException BindException
     * @return CommonResult
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonResult<BindException>> validationExceptionsHandle(final BindException bindException) {
        CommonResult<BindException> commonResult = new CommonResult<>();
        commonResult.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        commonResult.setMessage(bindException.getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(commonResult);
    }
}
