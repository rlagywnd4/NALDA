package com.sparta.nalda.service.menu;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;

public interface MenuService {
    void menuSave(CreateMenuRequestDto dto);
}
