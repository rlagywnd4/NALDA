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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * 메뉴수정
     * @param id
     * @param userId
     * @param storeId
     * @param menuName
     * @param menuContents
     * @param price
     */
    @Override
    public void updateMenu(Long id, Long userId, Long storeId, String menuName, String menuContents, Long price) {

        // 유저 확인
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 가게 확인
        StoreEntity store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));

        // 메뉴 수정 유저 권한 확인
        if (!store.getUser().equals(user)) {
            throw new IllegalArgumentException("메뉴 작성자만 수정 할 수 있습니다.");
        }

        // 메뉴 확인
        MenuEntity menu = menuRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

        // 수정할 요청 항목이 아무 값이 들어오지 않았을 경우
        if (menuName == null && menuContents == null && price == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 할 항목을 입력해주세요.");
        }

        if (menuName != null) {
            menu.setMenuName(menuName);
        }
        if (menuContents != null) {
            menu.setMenuContents(menuContents);
        }
        if (price != null) {
            menu.setPrice(price);
        }

        menuRepository.save(menu);
    }
}
