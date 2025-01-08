package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;
import com.sparta.nalda.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners/menus")
@RequiredArgsConstructor
public class OwnerMenuController {

    private final MenuService menuService;

    /**
     * 메뉴 생성
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<MessageResponse> menuSave (@Valid @RequestBody CreateMenuRequestDto dto) {
        menuService.menuSave(dto);
        return ResponseEntity.ok(new MessageResponse("메뉴가 생성되었습니다."));
    }
}
