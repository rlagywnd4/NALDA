package com.sparta.nalda.service.order;

import com.sparta.nalda.dto.order.OrderListResponseDto;
import com.sparta.nalda.dto.order.OrderRequestDto;
import com.sparta.nalda.dto.order.OrderResponseDto;
import com.sparta.nalda.dto.order.OwnerOrderResponseDto;
import com.sparta.nalda.entity.OrderEntity;

import com.sparta.nalda.util.OrderStatus;
import java.util.List;


public interface OrderService {

  OrderEntity createOrder(Long userId ,OrderRequestDto requestDto);

  OrderResponseDto findById(Long id);

  List<OrderListResponseDto> findAllOrderList();

  List<OwnerOrderResponseDto> findAllOrderListOwner(Long ownerId);

  void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}