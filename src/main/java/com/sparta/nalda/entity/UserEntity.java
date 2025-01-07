package com.sparta.nalda.entity;

import com.sparta.nalda.common.userRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    private Long id;
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private userRole userRole;

    public UserEntity(String email, String password, String address,
        com.sparta.nalda.common.userRole userRole) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.userRole = userRole;
    }

}
