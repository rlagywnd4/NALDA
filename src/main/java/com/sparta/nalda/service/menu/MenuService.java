package com.sparta.nalda.service.menu;

import com.sparta.nalda.dto.menu.MenuResponseDto;

public interface MenuService {
    void menuSave(Long storeId, String menuName, String menuContents, Long price);

    MenuResponseDto findById(Long id);

    void updateMenu(Long id, Long storeId, String menuName, String menuContents, Long price);

    void deleteMenu(Long id, Long storeId);
}
