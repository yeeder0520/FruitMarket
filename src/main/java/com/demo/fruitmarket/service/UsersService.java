package com.demo.fruitmarket.service;

import com.demo.fruitmarket.config.FruitMarketException;
import com.demo.fruitmarket.entity.UsersPO;
import com.demo.fruitmarket.repository.UsersRepo;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepo usersRepo;

    public UsersService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public UsersPO getUsers(String username) throws FruitMarketException {
       return usersRepo.findByUsername(username).orElseThrow(FruitMarketException::new);
    }
}
