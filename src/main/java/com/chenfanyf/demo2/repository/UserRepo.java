package com.chenfanyf.demo2.repository;

import com.chenfanyf.demo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
