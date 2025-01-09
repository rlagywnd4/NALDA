package com.sparta.nalda.repository;

import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.util.StoreStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    int countByUserAndStatus(UserEntity user, StoreStatus storeStatus);

    Page<StoreEntity> findByStoreNameContaining(String searchKeyword, Pageable pageable);
}
