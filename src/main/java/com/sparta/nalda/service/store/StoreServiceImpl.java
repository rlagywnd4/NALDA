package com.sparta.nalda.service.store;

import com.sparta.nalda.dto.store.StoreAndMenusResponseDto;
import com.sparta.nalda.dto.store.StoresResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.ReviewEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.ReviewRepository;
import com.sparta.nalda.repository.StoreRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.StoreStatus;
import com.sparta.nalda.util.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;

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

    @Override
    @Transactional
    public StoreAndMenusResponseDto getStoreAndMenus(Long storeId) {

        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store with ID " + storeId + " not found"));

        // TODO: menu를 가져올때 user는 가져오지 않을 수 있도록 하는 기능 추가(필수기능 마무리후)
        List<MenuEntity> menus = menuRepository.findAllByStoreId(storeId);

        return new StoreAndMenusResponseDto(store, menus);
    }

    @Override
    @Transactional
    public Page<StoresResponseDto> getStores(
            String searchKeyword,
            String sortBy,
            int page,
            int size
    ) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<StoreEntity> stores = storeRepository.findByStoreNameContaining(searchKeyword, pageable);

        return stores.map(store -> StoresResponseDto.of(store,getAverageStarScore(store)));
    }

    public String getAverageStarScore(StoreEntity store){
        List<ReviewEntity> reviews = reviewRepository.findStarScoreByStoreId(store.getId());

        return String.format("%.1f",
                reviews.stream().mapToDouble(ReviewEntity::getStarScore).average().orElse(0.0));
    }
}
