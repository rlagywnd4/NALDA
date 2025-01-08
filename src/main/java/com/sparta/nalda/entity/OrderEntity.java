package com.sparta.nalda.entity;

import com.sparta.nalda.util.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menu_orders")
@NoArgsConstructor
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menuId;

    public OrderEntity(OrderStatus orderStatus, UserEntity user, MenuEntity menu) {
        this.orderStatus = orderStatus;
        this.userId = user;
        this.menuId = menu;
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
