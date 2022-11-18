package com.demo.fruitmarket.entity;

import lombok.*;

import javax.persistence.*;


/**
 * 水果 PO
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Fruit")
public class FruitPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int price;
}
