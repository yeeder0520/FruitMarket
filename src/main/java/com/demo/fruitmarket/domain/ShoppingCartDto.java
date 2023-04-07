package com.demo.fruitmarket.domain;

import com.demo.fruitmarket.controller.requestCommand.PutIntoShoppingCartCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 購物車 DTO
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
    private String consumerName;
    private String fruitName;
    private int quantity;

    public ShoppingCartDto(PutIntoShoppingCartCommand command) {
        this.consumerName = command.getConsumerName();
        this.fruitName = command.getFruitName();
        this.quantity = command.getQuantity();
    }
}
