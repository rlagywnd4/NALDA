package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;
import com.sparta.nalda.dto.menu.MenuResponseDto;
import com.sparta.nalda.service.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        menuService.menuSave(
                dto.getUserId(),
                dto.getStoreId(),
                dto.getMenuName(),
                dto.getMenuContents(),
                dto.getPrice()
        );
        return ResponseEntity.ok(new MessageResponse("메뉴가 생성되었습니다."));
    }

    /**
     * 메뉴 단건 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.findById(id), HttpStatus.OK);
    }
}
