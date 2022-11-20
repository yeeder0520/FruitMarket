package com.demo.fruitmarket.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

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
//        return methodParameter.getParameterType().isAssignableFrom(CommonResult.class);
        return false;
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
        return CommonResult.success(body);
    }

    /**
     * 處理水果市場的異常
     *
     * @param e FruitMarketException
     * @return CommonResult
     */
    @ExceptionHandler(FruitMarketException.class)
    public CommonResult<FruitMarketException> fruitMarketExceptionHandle(FruitMarketException e) {
        CommonResult<FruitMarketException> commonResult = new CommonResult<>();
        commonResult.setStatusCode(e.getCode());
        commonResult.setMessage(e.getMessage());
        return commonResult;
    }

    /**
     * 處理 Validation
     *
     * @param exception BindException
     * @return CommonResult
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<BindException> validationExceptionsHandle(final BindException exception) {
        CommonResult<BindException> commonResult = new CommonResult<>();
        commonResult.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        commonResult.setMessage(exception.getAllErrors().get(0).getDefaultMessage());
        return commonResult;
    }
}
