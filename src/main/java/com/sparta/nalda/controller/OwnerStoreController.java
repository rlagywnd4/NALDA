package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.store.CreateStoreRequestDto;
import com.sparta.nalda.service.store.StoreServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners/stores")
@RequiredArgsConstructor
public class OwnerStoreController {

    private final StoreServiceImpl storeService;

    //TODO: 요청에서 AuthenticationPrincipal 이 부분은 대현님 진행상황에 맞춰 수정 필요
    @PostMapping
    public ResponseEntity<MessageResponse> saveStore(@RequestBody @Valid CreateStoreRequestDto dto) {
        //@AuthenticationPrincipal Long userId 매개변수에 추가 필요
        Long userId = 1L;
        storeService.saveStore(userId, dto.getStoreName(), dto.getStoreContents(), dto.getMinOrderPrice(), dto.getOpenTime(), dto.getCloseTime());
        return ResponseEntity.ok(new MessageResponse("가게가 생성되었습니다."));
    }

}
