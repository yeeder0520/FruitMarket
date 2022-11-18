package com.demo.fruitmarket.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception處理
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@RequiredArgsConstructor
@Getter
public class FruitMarketException extends Exception {

    /**
     * @param code    code
     * @param message message
     */
    public FruitMarketException(
            final String code,
            final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param code          code
     * @param message       message
     * @param messageDetail messageDetail
     */
    public FruitMarketException(
            final String code,
            final String message,
            final String messageDetail) {
        super(message);
        this.code = code;
        this.message = message;
        this.messageDetail = messageDetail;
    }

    /**
     * 錯誤代碼.
     */
    private String code;

    /**
     * 錯誤訊息.
     */
    private String message;

    /**
     * 錯誤訊息明細.
     */
    private String messageDetail;

}
