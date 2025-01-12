package com.sparta.nalda.service.store;

import com.sparta.nalda.dto.store.StoreAndMenusResponseDto;
import com.sparta.nalda.dto.store.StoresResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.ReviewEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.exception.ErrorCode;
import com.sparta.nalda.exception.NdException;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.ReviewRepository;
import com.sparta.nalda.repository.StoreRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.AuthUser;
import com.sparta.nalda.util.StoreStatus;
import com.sparta.nalda.util.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

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
            String storeName,
            String storeContents,
            Long minOrderPrice,
            LocalTime openTime,
            LocalTime closeTime
    ) {
        UserEntity user = userRepository.findByIdOrElseThrow(AuthUser.getId());

        if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            throw new NdException(ErrorCode.ROLE_MISMATCH);
        }

        int enabledStoreCount = storeRepository.countByUserAndStatus(user, StoreStatus.ENABLE);
        if (enabledStoreCount >= 3) {
            throw new NdException(ErrorCode.ENABLE_STORE_LIMITED_EXCEEDED);
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

        StoreEntity store = storeRepository.findByIdOrElseThrow(storeId);

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


    /**
     * 가게 수정
     * @param id
     * @param storeName
     * @param storeContents
     * @param minOrderPrice
     * @param openTime
     * @param closeTime
     */
    @Override
    @Transactional
    public void updateStore(Long id, String storeName, String storeContents, Long minOrderPrice, LocalTime openTime, LocalTime closeTime) {
        StoreEntity store = storeRepository.findByIdOrElseThrow(id);

        verifyIdentity(store);

        if (storeName == null && storeContents == null && minOrderPrice == null && openTime == null && closeTime == null) {
            throw new NdException(ErrorCode.MISSING_UPDATE_FIELD);
        }

        if (storeName != null) {
            store.setStoreName(storeName);
        }

        if (storeContents != null) {
            store.setStoreContents(storeContents);
        }

        if (minOrderPrice != null) {
            store.setMinOrderPrice(minOrderPrice);
        }

        if (openTime != null) {
            store.setOpenTime(openTime);
        }

        if (closeTime != null) {
            store.setCloseTime(closeTime);
        }

        storeRepository.save(store);
    }

    /**
     * 가게 폐업 처리
     * @param id
     */
    @Override
    @Transactional
    public void disableStore(Long id) {
        StoreEntity store = storeRepository.findByIdOrElseThrow(id);
        verifyIdentity(store);

        store.disableStore();
    }


    public String getAverageStarScore(StoreEntity store){
        List<ReviewEntity> reviews = reviewRepository.findStarScoreByStoreId(store.getId());

        return String.format("%.1f",
                reviews.stream().mapToDouble(ReviewEntity::getStarScore).average().orElse(0.0));
    }

    public void verifyIdentity(StoreEntity store){
        if(!store.getUser().getId().equals(AuthUser.getId())){
            throw new NdException(ErrorCode.STORE_OWNER_MISMATCH);
        }
    }
}
