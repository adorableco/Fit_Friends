package com.example.fit_friends.repository;

import com.example.fit_friends.domain.User;
import com.example.fit_friends.dto.ModifyUserDetailRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);



}
