package com.example.clean.code.repository;

import com.example.clean.code.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSaver extends JpaRepository<UserEntity, Long> {
}
