package com.demo.fruitmarket.service;

import com.demo.fruitmarket.controller.requestCmd.PutIntoShoppingCartCommand;
import com.demo.fruitmarket.entity.Fruit;
import com.demo.fruitmarket.repository.FruitMarketRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitMarketService {

    private final FruitMarketRepo fruitMarketRepo;

    public FruitMarketService(FruitMarketRepo fruitMarketRepo) {
        this.fruitMarketRepo = fruitMarketRepo;
    }

    /**
     * 業務邏輯 - 列出所有賣的水果跟單價
     *
     * @return List<Fruit>
     */
    public List<Fruit> getAllFruitAndPrice() {
        return fruitMarketRepo.findAll();
    }

    public void putIntoShoppingCart(PutIntoShoppingCartCommand command) {
        Fruit byName = fruitMarketRepo.findByName(command.getFruitName());
        System.out.println("byName = " + byName.toString());
    }
}
