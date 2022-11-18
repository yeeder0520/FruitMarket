package com.demo.fruitmarket.controller;

import com.demo.fruitmarket.config.FruitMarketException;
import com.demo.fruitmarket.controller.command.PutIntoShoppingCartCommand;
import com.demo.fruitmarket.controller.representation.GetAllFruitAndPriceRepresentation;
import com.demo.fruitmarket.entity.FruitPO;
import com.demo.fruitmarket.service.FruitMarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 水果市場 Controller.
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
@RestController
@RequestMapping(value = "/fruitMarket")
public class FruitMarketController {

    private final FruitMarketService fruitMarketService;

    public FruitMarketController(FruitMarketService fruitMarketService) {
        this.fruitMarketService = fruitMarketService;
    }

    /**
     * API - 列出所有賣的水果跟單價
     *
     * @return ResponseEntity
     */
    @GetMapping(value = "/getAllFruitAndPrice")
    public ResponseEntity<Set<GetAllFruitAndPriceRepresentation>> getAllFruitAndPrice() {
        return ResponseEntity.ok(fruitMarketService.getAllFruitAndPrice());
    }

    /**
     * API - 將東西放進購物車
     *
     * @param command command
     * @return ResponseEntity
     */
    @GetMapping(value = "/putIntoShoppingCart")
    public ResponseEntity<String> putIntoShoppingCart(
            @Validated @RequestBody PutIntoShoppingCartCommand command
    ) throws FruitMarketException {
        fruitMarketService.putIntoShoppingCart(command);
        return ResponseEntity.ok().build();
    }
}
