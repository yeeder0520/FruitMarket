package com.demo.fruitmarket.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
public class CommonResult<T> {
    private String statusCode;
    private String message;
    private T data;


    /**
     * 產生成功
     *
     * @param data data
     * @param <T>  <T>
     * @return CommonResult
     */
    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.statusCode = "200";
        commonResult.message = "成功~~  ";
        commonResult.data = data;
        return commonResult;
    }

    /**
     * 產生錯誤.
     *
     * @param exception FruitMarketException
     * @return CommonResult
     */
    public static <T> CommonResult<T> fail(
            final FruitMarketException exception
    ) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setStatusCode(exception.getCode());
        commonResult.setMessage(exception.getMessage());
        return commonResult;
    }
}
