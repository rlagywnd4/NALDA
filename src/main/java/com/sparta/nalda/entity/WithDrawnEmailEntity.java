package com.sparta.nalda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "with_drawn_emails")
public class WithDrawnEmailEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String email;

    public WithDrawnEmailEntity(String email) {
        this.email = email;
    }
}
