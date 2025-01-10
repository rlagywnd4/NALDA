package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.menu.CreateMenuRequestDto;
import com.sparta.nalda.dto.menu.DeleteMenuRequestDto;
import com.sparta.nalda.dto.menu.UpdateMenuRequestDto;
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
     *
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<MessageResponse> menuSave(@Valid @RequestBody CreateMenuRequestDto dto) {
        menuService.menuSave(
                dto.getStoreId(),
                dto.getMenuName(),
                dto.getMenuContents(),
                dto.getPrice()
        );
        return ResponseEntity.ok(new MessageResponse("메뉴가 생성되었습니다."));
    }



    /**
     * 메뉴 수정
     * @param id
     * @param dto
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateMenu(@PathVariable Long id, @Valid @RequestBody UpdateMenuRequestDto dto) {
        menuService.updateMenu(
                id,
                dto.getStoreId(),
                dto.getMenuName(),
                dto.getMenuContents(),
                dto.getPrice()
        );

        return ResponseEntity.ok(new MessageResponse("메뉴 수정이 완료되었습니다."));
    }

    /**
     * 메뉴 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteMenu (@PathVariable Long id, @Valid @RequestBody DeleteMenuRequestDto dto) {
        menuService.deleteMenu(id, dto.getStoreId());
        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));
    }
}
