package com.demo.fruitmarket.repository;

import com.demo.fruitmarket.entity.FruitPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 水果市場 Repository
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
public interface FruitMarketRepo extends JpaRepository<FruitPO, Long> {
    Optional<FruitPO> findByName(String name);
}
