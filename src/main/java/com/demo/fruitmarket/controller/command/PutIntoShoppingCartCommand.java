package com.demo.fruitmarket.controller.command;

import lombok.*;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "用戶名稱不得為空")
    private String consumerName;
    @NotNull(message = "水果名稱不得為空")
    private String fruitName;
    @NotNull(message = "購買數量不得為空")
    private int quantity;
}
