package com.sparta.nalda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String reviewContents;

    @Column
    @Range(min = 1, max = 5)
    private Integer starScore;


    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ReviewEntity(OrderEntity order, String reviewContents, Integer starScore) {
        this.order = order;
        this.reviewContents = reviewContents;
        this.starScore = starScore;
    }

    public ReviewEntity() {
    }
}
