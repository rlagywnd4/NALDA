package com.sparta.nalda.controller;

import com.sparta.nalda.dto.store.StoreAndMenusResponseDto;
import com.sparta.nalda.service.store.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreServiceImpl storeService;

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreAndMenusResponseDto> getStoreAndMenus(@PathVariable Long storeId){
        return ResponseEntity.ok(storeService.getStoreAndMenus(storeId));
    }

}
