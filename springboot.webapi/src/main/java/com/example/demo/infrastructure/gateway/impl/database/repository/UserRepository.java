package com.example.demo.infrastructure.gateway.impl.database.repository;

import com.example.demo.infrastructure.gateway.impl.database.model.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDO,String> {
    List<UserDO> findByUserName(String name);
}
