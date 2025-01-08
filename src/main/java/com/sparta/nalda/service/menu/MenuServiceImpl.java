package com.sparta.nalda.service.menu;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;
import com.sparta.nalda.dto.menu.MenuResponseDto;
import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.StoreRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.StoreStatus;
import com.sparta.nalda.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    /**
     * 메뉴 생성
     * @param userId
     * @param storeId
     * @param menuName
     * @param menuContents
     * @param price
     */
    @Override
    public void menuSave(Long userId, Long storeId, String menuName, String menuContents, Long price) {

        // 유저 확인
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 메뉴 생성 유저 권한 확인
        if (!UserRole.OWNER.equals(user.getUserRole())) {
            throw new IllegalArgumentException("메뉴를 생성할 권한이 없습니다.");
        }

        // 가게 확인
        StoreEntity store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        // 가게가 폐업했을 때
        if (!StoreStatus.ENABLE.equals(store.getStatus())) {
            throw new IllegalArgumentException("폐업한 가게에는 메뉴를 추가할 수 없습니다.");
        }

        MenuEntity menu = new MenuEntity(store, menuName, menuContents, price);

        menuRepository.save(menu);
    }

    /**
     * 메뉴 단건 조회
     * @param id
     * @return
     */
    @Override
    public MenuResponseDto findById(Long id) {
        MenuEntity menu = menuRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
        return new MenuResponseDto(menu);
    }
}
