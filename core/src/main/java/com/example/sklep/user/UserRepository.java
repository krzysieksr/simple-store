package com.example.sklep.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    @Override
    @Query("SELECT distinct u FROM User u JOIN FETCH u.deliveryAddressEntity")
    List<UserEntity> findAll();
}
