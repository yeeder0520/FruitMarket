package com.demo.fruitmarket.repository;

import com.demo.fruitmarket.entity.UsersPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<UsersPO,String> {
    Optional<UsersPO> findByUsername(String username);
}
