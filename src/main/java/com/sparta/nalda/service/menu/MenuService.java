package com.sparta.nalda.service.menu;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;
import com.sparta.nalda.dto.menu.MenuResponseDto;

public interface MenuService {
    void menuSave(Long userId, Long storeId, String menuName, String menuContents, Long price);

    MenuResponseDto findById(Long id);
}
