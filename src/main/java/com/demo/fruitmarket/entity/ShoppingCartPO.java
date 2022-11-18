package com.demo.fruitmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 購物車 PO
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ShoppingCart")
public class ShoppingCartPO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Id
    private String consumerName;
    private String fruitName;
    private int quantity;

    public ShoppingCartPO(String consumerName, String fruitName, int quantity) {
        this.consumerName = consumerName;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }
}
