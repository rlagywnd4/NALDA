package com.sparta.nalda.controller;
import com.sparta.nalda.dto.menu.MenuResponseDto;
import com.sparta.nalda.service.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    /**
     * 메뉴 단건 조회
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(menuService.findById(id), HttpStatus.OK);
    }
}
