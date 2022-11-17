package com.demo.fruitmarket;

import com.demo.fruitmarket.entity.Fruit;
import com.demo.fruitmarket.repository.FruitMarketRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FruitMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(FruitMarketApplication.class, args);
	}
}
