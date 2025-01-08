package com.sparta.nalda.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menus")
public class MenuEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String menuName;

    @Column(length = 50)
    private String menuContents;

    @Column
    private Long price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity storeId;
}
