package com.sparta.nalda.repository;

import com.sparta.nalda.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    default UserEntity findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

}
