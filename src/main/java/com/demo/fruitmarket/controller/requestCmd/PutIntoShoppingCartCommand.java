package com.demo.fruitmarket.controller.requestCmd;

import lombok.*;

/**
 * 將水果放進購物車的請求參數
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutIntoShoppingCartCommand {
    private String consumerName;
    private String fruitName;
    private int quantity;
}
