package com.sparta.nalda.entity;

import com.sparta.nalda.util.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(length = 255)
    private String address;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserEntity(String email, String password, String address,UserRole userRole) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.userRole = userRole;
    }

}
