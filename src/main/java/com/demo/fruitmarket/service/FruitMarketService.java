package com.demo.fruitmarket.service;

import com.demo.fruitmarket.entity.Fruit;
import com.demo.fruitmarket.repository.FruitMarketRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitMarketService {

    private final FruitMarketRepo fruitMarketRepo;

    public FruitMarketService(FruitMarketRepo fruitMarketRepo) {
        this.fruitMarketRepo = fruitMarketRepo;
    }

    public List<Fruit> getAllFruitAndPrice() {
        return fruitMarketRepo.findAll();
    }
}
