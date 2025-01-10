package com.sparta.nalda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "menus")
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 20)
    private String menuName;

    @Setter
    @Column(length = 50)
    private String menuContents;

    @Setter
    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    public MenuEntity(StoreEntity store, String menuName, String menuContents, Long price) {
        this.store = store;
        this.menuName = menuName;
        this.menuContents = menuContents;
        this.price = price;
    }

    public MenuEntity() {

    }
}
