package com.demo.fruitmarket.controller;

import com.demo.fruitmarket.config.CommonResult;
import com.demo.fruitmarket.config.FruitMarketException;
import com.demo.fruitmarket.controller.requestCommand.PutIntoShoppingCartCommand;
import com.demo.fruitmarket.controller.responseCommand.GetAllFruitAndPriceRepresentation;
import com.demo.fruitmarket.service.FruitMarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CommonResult<Set<GetAllFruitAndPriceRepresentation>> getAllFruitAndPrice() {
        return CommonResult.success(fruitMarketService.getAllFruitAndPrice());
    }

    /**
     * API - 將東西放進購物車
     *
     * @param command command
     * @return ResponseEntity
     */
    @GetMapping(value = "/putIntoShoppingCart")
    public ResponseEntity<CommonResult<Void>> putIntoShoppingCart(
            @Valid @RequestBody PutIntoShoppingCartCommand command
    ) throws FruitMarketException {
        fruitMarketService.putIntoShoppingCart(command);
        return ResponseEntity.ok(CommonResult.success());
    }


    @GetMapping("/buyFruit/{buyCount}")
    public String buyFruit(@PathVariable int buyCount) {
        return fruitMarketService.buyFruit(buyCount);
    }
}
