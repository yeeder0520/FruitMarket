package com.demo.fruitmarket.controller;

import com.demo.fruitmarket.config.CommonResult;
import com.demo.fruitmarket.config.FruitMarketException;
import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 水果市場 Controller.
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@RestController
@RequestMapping(value = "/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/getUsers")
    public ResponseEntity<CommonResult<UsersPO>> getUsers(String username) throws FruitMarketException {
        return ResponseEntity.ok(CommonResult.success(usersService.getUsers(username)));
    }
}
