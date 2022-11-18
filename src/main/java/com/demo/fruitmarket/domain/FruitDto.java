package com.demo.fruitmarket.domain;

import com.demo.fruitmarket.entity.FruitPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 水果 DTO
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FruitDto {
    private String name;
    private int price;

    public FruitDto(FruitPO fruitPO) {
        this.name = fruitPO.getName();
        this.price = fruitPO.getPrice();
    }
}
