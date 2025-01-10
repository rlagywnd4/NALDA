package com.sparta.nalda.repository;

import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.exception.ErrorCode;
import com.sparta.nalda.exception.NdException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    default UserEntity findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new NdException(ErrorCode.USER_NOT_FOUND));
    }

    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

}
