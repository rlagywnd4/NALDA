package com.sparta.nalda.service.menu;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;
import com.sparta.nalda.dto.menu.MenuResponseDto;
import com.sparta.nalda.dto.menu.UpdateMenuRequestDto;
import jakarta.validation.Valid;

public interface MenuService {
    void menuSave(Long userId, Long storeId, String menuName, String menuContents, Long price);

    MenuResponseDto findById(Long id);

    void updateMenu(Long id, Long userId, Long storeId, String menuName, String menuContents, Long price);

}
