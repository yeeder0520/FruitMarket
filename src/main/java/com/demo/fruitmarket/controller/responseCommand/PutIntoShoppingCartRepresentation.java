package com.demo.fruitmarket.controller.responseCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 購物車的回傳參數
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PutIntoShoppingCartRepresentation {
    private String consumerName;
    private String fruitName;
    private int quantity;
    private int totalCost;
}
