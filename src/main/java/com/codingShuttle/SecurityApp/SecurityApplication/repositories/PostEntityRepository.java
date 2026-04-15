package com.codingShuttle.SecurityApp.SecurityApplication.repositories;

import com.codingShuttle.SecurityApp.SecurityApplication.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
}