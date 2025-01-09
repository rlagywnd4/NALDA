package com.sparta.nalda.repository;

import com.sparta.nalda.entity.WithDrawnEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithDrawnEmailRepository extends JpaRepository<WithDrawnEmailEntity, Long> {

    boolean existsByEmail(String email);
}
