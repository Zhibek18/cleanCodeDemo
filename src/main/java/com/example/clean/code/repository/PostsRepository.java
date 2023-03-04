package com.example.clean.code.repository;

import com.example.clean.code.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<PostEntity, Long> {
}
