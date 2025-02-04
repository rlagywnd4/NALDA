package com.sparta.nalda.service.order;

import com.sparta.nalda.dto.order.OrderListResponseDto;
import com.sparta.nalda.dto.order.OrderRequestDto;
import com.sparta.nalda.dto.order.OrderResponseDto;
import com.sparta.nalda.dto.order.OwnerOrderResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.OrderEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.exception.ErrorCode;
import com.sparta.nalda.exception.NdException;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.OrderRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.OrderStatus;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final MenuRepository menuRepository;

  @Transactional
  @Override
  public OrderEntity createOrder(Long userId,OrderRequestDto requestDto) {
    // 사용자와 메뉴를 DB에서 조회
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    MenuEntity menu = menuRepository.findById(requestDto.getMenuId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

    LocalTime now = LocalTime.now();

    LocalTime closeTime = menu.getStore().getCloseTime();
    LocalTime openTime = menu.getStore().getOpenTime();
    Long minPrice = menu.getStore().getMinOrderPrice();

    if ((closeTime.isBefore(openTime) && (now.isAfter(closeTime) && now.isBefore(openTime))) ||
        (closeTime.isAfter(openTime) && (now.isAfter(closeTime) || now.isBefore(openTime)))) {
      throw new NdException(ErrorCode.TIME_OVER);
    }

    if (menu.getPrice() < minPrice) {
      throw new NdException(ErrorCode.MIN_ORDER_PRICE_NOT_OVER);
    }

    // 주문 엔티티 생성
    OrderEntity order = new OrderEntity(OrderStatus.PENDING, user, menu);

    // 주문 저장
    return orderRepository.save(order);

  }

  @Override
  public OrderResponseDto findById(Long id) {


    // 주문 조회
    OrderEntity findOrder = orderRepository.findByIdOrElseThrow(id);

    StoreEntity store = findOrder.getMenu().getStore();
    MenuEntity menu = findOrder.getMenu();

    //상태,유저,메뉴,가게이름,가격
    return new OrderResponseDto(
        findOrder.getOrderStatus(),
        findOrder.getCreatedAt(),
        findOrder.getMenu().getMenuName(),
        store.getStoreName(),
        menu.getPrice());
  }

  @Override
  public List<OrderListResponseDto> findAllOrderList() {

    List<OrderEntity> orders = orderRepository.findAll();

    // 리스트 형식으로 변환
    return orders.stream()
//        .map(OrderListResponseDto::new)
        //        .map(OrderListResponseDto::order)
        .map(order -> new OrderListResponseDto(
            order.getId(),
            order.getOrderStatus(),
            order.getCreatedAt(),
            order.getMenu().getMenuName(),
            order.getMenu().getStore().getStoreName(),
            order.getMenu().getPrice()
        ))
        .toList();

  }



  @Override
  public List<OwnerOrderResponseDto> findAllOrderListOwner(Long ownerId) {

    List<OrderEntity> orders = orderRepository.findAllByMenuStoreUserId(ownerId);

    // 리스트 형식으로 변환
    return orders.stream()
        .map(order -> new OwnerOrderResponseDto(
            order.getId(),
            order.getUser().getEmail(),
            order.getUser().getAddress(),
            order.getMenu().getMenuName(),
            order.getMenu().getStore().getStoreName(),
            order.getMenu().getPrice()
        ))
        .toList();


  }

  @Override
  @Transactional
  public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {

    // 주문 조회 및 예외 처리
    OrderEntity order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

    // 현재 상태와 변경하려는 상태가 동일한 경우 예외 발생
    if (order.getOrderStatus() == orderStatus) {
      throw new IllegalArgumentException("이미 '" + orderStatus + "' 상태입니다.");
    }

    // 상태 업데이트
    order.updateStatus(orderStatus);
  }

}
