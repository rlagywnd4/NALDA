package com.sparta.nalda.service.store;

import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.StoreRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.StoreStatus;
import com.sparta.nalda.util.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void saveStore(
            Long userId,
            String storeName,
            String storeContents,
            Long minOrderPrice,
            LocalTime openTime,
            LocalTime closeTime
    ) {
        //TODO: 예외상황 수정 필요
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            throw new IllegalArgumentException("customer can't create store");
        }

        int enabledStoreCount = storeRepository.countByUserAndStatus(user, StoreStatus.ENABLE);
        if (enabledStoreCount >= 3) {
            throw new IllegalArgumentException("활성화된 가게는 3개이상 생성할 수 없습니다.");
        }

        StoreEntity store = StoreEntity.builder()
                .user(user)
                .storeName(storeName)
                .storeContents(storeContents)
                .minOrderPrice(minOrderPrice)
                .openTime(openTime)
                .closeTime(closeTime)
                .status(StoreStatus.ENABLE)
                .build();

        storeRepository.save(store);
    }

}
