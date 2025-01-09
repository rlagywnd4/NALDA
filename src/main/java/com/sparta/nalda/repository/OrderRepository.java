package com.sparta.nalda.repository;

import com.sparta.nalda.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  default OrderEntity findByIdOrElseThrow(Long id) {
    return findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
  }

}
