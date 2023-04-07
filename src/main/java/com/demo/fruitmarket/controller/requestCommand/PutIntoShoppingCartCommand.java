package com.demo.fruitmarket.controller.requestCommand;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "用戶名稱一定要帶")
    private String consumerName;

    @NotBlank(message = "水果名稱一定要帶")
    private String fruitName;

    @NotNull(message = "購買數量一定要帶")
    @Min(message = "購買數量至少一個", value = 1)
    private int quantity;
}
