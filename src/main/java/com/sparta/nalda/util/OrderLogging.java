package com.sparta.nalda.util;

import com.sparta.nalda.dto.order.OrderRequestDto;
import com.sparta.nalda.entity.OrderEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class OrderLogging {

    @Around("execution(* com.sparta.nalda.service.order.OrderService.createOrder(..))")
    public Object createLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        LocalDateTime requestTime = LocalDateTime.now();

        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof OrderRequestDto) {
            OrderRequestDto dto = (OrderRequestDto) args[0];
            log.info("==============주문요청===============");
            log.info("고객ID : {}", dto.getUser());
            log.info("메뉴ID : {}", dto.getMenu());
            log.info("요청시각 : {}", requestTime);
            log.info("====================================");

        }

        try {
            // 메서드 실행
            Object result = joinPoint.proceed();

            // 반환값이 OrderEntity인 경우 로깅
            if (result instanceof OrderEntity) {
                OrderEntity order = (OrderEntity) result;

                log.info("==============주문생성완료===============");
                log.info("주문ID : {}", order.getUser().getId());
                log.info("고객ID : {}", order.getUser().getEmail());
                log.info("메뉴ID : {}", order.getMenu().getId());
                log.info("주문상태 : {}", order.getOrderStatus());
                log.info("요청시각 : {}", requestTime);
                log.info("====================================");

            }

            return result;
        } catch (Exception e) {
            log.info("==============주문실패===============");
            log.error("주문 실패 : {}", e.getMessage());
            log.info("요청시각 : {}", requestTime);
            log.info("====================================");

            throw e;
        }
    }

    @Around("execution(* com.sparta.nalda.service.order.OrderService.updateOrderStatus(..))")
    public Object updateLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        LocalDateTime requestTime = LocalDateTime.now();

        Object[] args = joinPoint.getArgs();

        if (args.length >= 2 && args[0] instanceof Long && args[1] instanceof OrderStatus) {
            Long orderId = (Long) args[0];
            OrderStatus newStatus = (OrderStatus) args[1];

            log.info("==============주문상태변경 요청===============");
            log.info("주문ID : {}", orderId);
            log.info("변경할 상태 : {}", newStatus);
            log.info("요청시각 : {}", requestTime);
            log.info("====================================");
        }

        try {
            Object result = joinPoint.proceed();

            log.info("==============주문상태변경 완료===============");
            if (result instanceof OrderEntity) {
                OrderEntity order = (OrderEntity) result;
                log.info("주문ID : {}", order.getId());
                log.info("고객ID : {}", order.getUser().getEmail());
                log.info("메뉴ID : {}", order.getMenu().getId());
                log.info("현재상태 : {}", order.getOrderStatus());
            }
            log.info("요청시각 : {}", requestTime);
            log.info("====================================");

            return result;
        } catch (Exception e) {
            log.info("==============주문상태변경 실패===============");
            log.error("실패 이유 : {}", e.getMessage());
            log.info("요청시각 : {}", requestTime);
            log.info("====================================");

            throw e;
        }
    }
}
