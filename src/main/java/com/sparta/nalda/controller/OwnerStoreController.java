package com.sparta.nalda.controller;

import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.store.CreateStoreRequestDto;
import com.sparta.nalda.service.store.StoreServiceImpl;
import com.sparta.nalda.util.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners/stores")
@RequiredArgsConstructor
public class OwnerStoreController {

    private final StoreServiceImpl storeService;

    //TODO: 요청에서 AuthenticationPrincipal 이 부분은 대현님 진행상황에 맞춰 수정 필요
    @PostMapping
    public ResponseEntity<MessageResponse> saveStore(@RequestBody @Valid CreateStoreRequestDto dto) {
        //@AuthenticationPrincipal Long userId 매개변수에 추가 필요
        Long userId = AuthUser.getId();
        storeService.saveStore(userId, dto.getStoreName(), dto.getStoreContents(), dto.getMinOrderPrice(), dto.getOpenTime(), dto.getCloseTime());
        return ResponseEntity.ok(new MessageResponse("가게가 생성되었습니다."));
    }

    /**
     * 가게 수정
     * @param id
     * @param dto
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateStore(@PathVariable Long id, @Valid @RequestBody CreateStoreRequestDto dto) {
        storeService.updateStore(
                id,
                dto.getStoreName(),
                dto.getStoreContents(),
                dto.getMinOrderPrice(),
                dto.getOpenTime(),
                dto.getCloseTime()
        );
        return ResponseEntity.ok(new MessageResponse("가게 정보 수정되었습니다."));
    }

    /**
     * 가게 폐업 처리
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> disableStore(@PathVariable Long id) {
        storeService.disableStore(id);

        return ResponseEntity.ok(new MessageResponse("폐업 처리가 완료 되었습니다."));
    }
}
