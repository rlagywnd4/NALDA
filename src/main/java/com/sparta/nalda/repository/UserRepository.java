package com.sparta.nalda.repository;

import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.exception.ErrorCode;
import com.sparta.nalda.exception.NdException;
import com.sparta.nalda.util.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus status);

    default UserEntity findByIdOrElseThrow(long id) {
        return findByIdAndStatus(id, UserStatus.ENABLED).orElseThrow(() -> new NdException(ErrorCode.USER_NOT_FOUND));
    }

    @Query("SELECT u FROM UserEntity u WHERE u.status = 'ENABLED'")
    List<UserEntity> findAllEnabledUsers();

    boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

}
