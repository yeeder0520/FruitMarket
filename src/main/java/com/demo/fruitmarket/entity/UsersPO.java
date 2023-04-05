package com.demo.fruitmarket.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Data
@Table(name = UsersPO.TABLE_NAME)
public class UsersPO {

    static final String TABLE_NAME = "USERS";

    @Id
    private String id;
    private String username;
    private String secret;
}
