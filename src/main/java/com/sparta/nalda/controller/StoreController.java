package com.sparta.nalda.controller;

import com.sparta.nalda.dto.store.StoreAndMenusResponseDto;
import com.sparta.nalda.dto.store.StoresResponseDto;
import com.sparta.nalda.service.store.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public ResponseEntity<Page<StoresResponseDto>> getStores(@RequestParam String searchKeyword, @RequestParam String sortBy, @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(storeService.getStores(searchKeyword, sortBy, page, size));
    }

}
