package com.sparta.nalda.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.util.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserFindByIdOrElseThrow_Success() {
        //given
        UserEntity user = new UserEntity("test@test.com", "12345678", "주소", UserRole.CUSTOMER);
        UserEntity saveUser = userRepository.save(user);

        //when
        userRepository.save(user);
        UserEntity actualResult = userRepository.findByIdOrElseThrow(saveUser.getId());

        //then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(saveUser.getId());
        assertThat(actualResult.getEmail()).isEqualTo(saveUser.getEmail());
        assertThat(actualResult.getPassword()).isEqualTo(saveUser.getPassword());
        assertThat(actualResult.getAddress()).isEqualTo(saveUser.getAddress());
        assertThat(actualResult.getUserRole()).isEqualTo(saveUser.getUserRole());
    }

    @Test
    void UserFindByIdOrElseThrow_Exception() {
        //given & when
        Exception exception = assertThrows(Exception.class, () ->
            userRepository.findByIdOrElseThrow(99L));

        //then
        assertEquals(exception.getMessage(), "User not found");
    }

    @Test
    void existsByEmail_Exist() {
        //given
        UserEntity user = new UserEntity("test@test.com", "12345678", "주소", UserRole.CUSTOMER);
        userRepository.save(user);

        //when
        boolean result = userRepository.existsByEmail(user.getEmail());

        //then
        assertThat(result).isTrue();
    }

    @Test
    void existsByEmail_NotExist() {
        //given & when
        boolean result = userRepository.existsByEmail("test@test.com");

        //then
        assertThat(result).isFalse();
    }

    @Test
    void FindByEmail_Success() {
        //given
        UserEntity user = new UserEntity("test@test.com", "12345678", "주소", UserRole.CUSTOMER);
        UserEntity saveUser = userRepository.save(user);

        //when
        userRepository.save(user);
        UserEntity actualResult = userRepository.findByEmail(saveUser.getEmail());

        //then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(saveUser.getId());
        assertThat(actualResult.getEmail()).isEqualTo(saveUser.getEmail());
        assertThat(actualResult.getPassword()).isEqualTo(saveUser.getPassword());
        assertThat(actualResult.getAddress()).isEqualTo(saveUser.getAddress());
        assertThat(actualResult.getUserRole()).isEqualTo(saveUser.getUserRole());
    }




}