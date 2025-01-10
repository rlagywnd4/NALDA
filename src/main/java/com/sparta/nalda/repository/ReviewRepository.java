package com.sparta.nalda.repository;

import com.sparta.nalda.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findStarScoreByStoreId(Long storeId);
}
