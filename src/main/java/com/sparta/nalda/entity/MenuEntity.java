package com.sparta.nalda.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "menus")
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String menuName;

    @Column(length = 50)
    private String menuContents;

    @Column(nullable = false)
    private Long price;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity storeId;

    public MenuEntity(String menuName, String menuContents, Long price) {
        this.menuName = menuName;
        this.menuContents = menuContents;
        this.price = price;
    }

    public MenuEntity() {

    }
}
