package com.codingShuttle.SecurityApp.SecurityApplication.repositories;

import com.codingShuttle.SecurityApp.SecurityApplication.entities.SessionEntity;
import com.codingShuttle.SecurityApp.SecurityApplication.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionEntityRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByToken(String token);
    Optional<SessionEntity> findByUser(User user);



    void deleteByUser(User user);
}