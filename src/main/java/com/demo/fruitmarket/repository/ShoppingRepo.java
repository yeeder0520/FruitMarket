package com.demo.fruitmarket.repository;

import com.demo.fruitmarket.entity.ShoppingCartPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 購物車 Repository
 *
 * @author YeeDer
 * @version 1.0.0
 * @since 2022/11/18 下午 05:29
 **/
public interface ShoppingRepo extends JpaRepository<ShoppingCartPO, Long> {
    Optional<ShoppingCartPO> findByConsumerName(String consumerName);
}
