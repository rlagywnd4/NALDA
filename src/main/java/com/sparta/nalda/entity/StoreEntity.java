package com.sparta.nalda.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.nalda.util.StoreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stores")
public class StoreEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String storeName;
    @Column(length = 50)
    private String storeContents;

    private Long minOrderPrice;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}