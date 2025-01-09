package com.sparta.nalda.repository;

import com.sparta.nalda.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  default OrderEntity findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
  }

  // 특정 사용자(userId)의 주문 목록 조회
  List<OrderEntity> findAll();


  List<OrderEntity> findAllByMenu_Store_User_Id(Long ownerId);



}
