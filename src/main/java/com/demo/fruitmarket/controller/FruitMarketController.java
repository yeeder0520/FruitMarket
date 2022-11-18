package com.demo.fruitmarket.controller;

import com.demo.fruitmarket.controller.requestCmd.PutIntoShoppingCartCommand;
import com.demo.fruitmarket.entity.Fruit;
import com.demo.fruitmarket.service.FruitMarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fruitMarket")
public class FruitMarketController {

    private final FruitMarketService fruitMarketService;

    public FruitMarketController(FruitMarketService fruitMarketService) {
        this.fruitMarketService = fruitMarketService;
    }

    @GetMapping(value = "/ping")
    public String ping() {
        return "pong";
    }

    /**
     * 列出所有賣的水果跟單價
     */
    @GetMapping(value = "/getAllFruitAndPrice")
    public ResponseEntity<List<Fruit>> getAllFruitAndPrice() {
        return ResponseEntity.ok(fruitMarketService.getAllFruitAndPrice());
    }

    @GetMapping(value = "/putIntoShoppingCart")
    public ResponseEntity<Void> putIntoShoppingCart(@RequestBody PutIntoShoppingCartCommand command) {
        fruitMarketService.putIntoShoppingCart(command);
        return ResponseEntity.ok().build();
    }
}
