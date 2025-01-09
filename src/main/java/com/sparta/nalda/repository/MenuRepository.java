package com.sparta.nalda.repository;

import com.sparta.nalda.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
}