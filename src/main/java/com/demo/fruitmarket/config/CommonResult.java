package com.demo.fruitmarket.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ResponseEntity 定義層
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult implements Serializable {
    private static final Long serialVersionUid = 1L;
    private String statusCode;
    private String message;
    private Object body;


    /**
     * 產生錯誤.
     *
     * @param exception FruitMarketException
     * @return CommonResult
     */
    public static CommonResult fail(
            final FruitMarketException exception
    ) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatusCode(exception.getCode());
        commonResult.setMessage(exception.getMessage());
        return commonResult;
    }
}
