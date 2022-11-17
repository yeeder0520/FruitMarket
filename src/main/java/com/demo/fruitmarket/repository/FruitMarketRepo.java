package com.demo.fruitmarket.repository;

import com.demo.fruitmarket.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitMarketRepo extends JpaRepository<Fruit,Long> {

}
