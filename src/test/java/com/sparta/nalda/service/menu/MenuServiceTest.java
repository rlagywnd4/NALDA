package com.sparta.nalda.service.menu;


import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.exception.NdException;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.StoreRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.AuthUser;
import com.sparta.nalda.util.StoreStatus;
import com.sparta.nalda.util.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @InjectMocks
    private MenuServiceImpl menuService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private AuthUser authUser;

    private MockedStatic<AuthUser> mockAuthUser;

    @BeforeEach
    void 기본세팅() {
        mockAuthUser = mockStatic(AuthUser.class);

        UserEntity user = new UserEntity(
                1L,
                "test@test.com",
                "Dlehdrjs12!",
                "address",
                UserRole.OWNER
        );
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(user);

        UserEntity savedUser = userRepository.save(user);
        mockAuthUser.when(AuthUser::getId).thenReturn(savedUser.getId());
    }

    //MockAuthUser 해제 , 데이터베이스 초기화
    @AfterEach
    void tearDown() {
        if (mockAuthUser != null) {
            mockAuthUser.close();
        }
        userRepository.deleteAll();
    }

    @Test
    void 메뉴생성() {
        //Given
        Long userId = 1L;
        Long storeId = 1L;
        String menuName = "짜장면";
        String menuContents = "짜장면입니다.";
        Long price = 7000L;

        UserEntity user = new UserEntity(userId, "test@naver.com", "Dlehdrjs12!", "경남", UserRole.OWNER);

        StoreEntity store = StoreEntity.builder()
                .id(storeId)
                .storeName("중식")
                .storeContents("중식입니다.")
                .user(user)
                .status(StoreStatus.ENABLE)
                .build();

        Mockito.when(AuthUser.getId()).thenReturn(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        //When
        menuService.menuSave(storeId, menuName, menuContents, price);

        //Then
        Mockito.verify(menuRepository).save(Mockito.any(MenuEntity.class));
    }

    @Test
    void 메뉴생성_없는유저_예외처리() {
        //Given
        Long userId = 1L;
        Long storeId = 1L;

        when(AuthUser.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NdException.class,
                () -> menuService.menuSave(storeId, "짬뽕", "짬뽕입니다.", 8000L));
    }

    @Test
    void 메뉴생성_손님_예외처리() {
        //Given
        Long userId = 1L;
        Long storeId = 1L;

        UserEntity user = new UserEntity(userId, "test2@test@com", "Dlehdrjs12!", "경남", UserRole.CUSTOMER);

        when(AuthUser.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //Then
        Assertions.assertThrows(NdException.class,
                () -> menuService.menuSave(storeId, "탕수육", "탕수육 입니다.", 13000L));
    }

    @Test
    void 메뉴생성_없는가게_예외처리() {
        // Given
        Long userId = 1L;
        Long storeId = 1L;

        UserEntity user = new UserEntity(userId, "test@test.com", "Dlehdrjs12!", "부산", UserRole.OWNER);

        when(AuthUser.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(NdException.class,
                () -> menuService.menuSave(storeId, "라조기", "라조기 입니다.", 16000L));
    }

    @Test
    void 메뉴생성_다른오너_예외처리() {
        //Given
        Long userId = 1L;
        Long storeId = 1L;

        UserEntity user = new UserEntity(userId, "test@test.com", "Dlehdrjs12!", "서울", UserRole.OWNER);
        UserEntity anotherUser = new UserEntity(2L, "test2@test.com", "Dlehdrjs12!", "부산", UserRole.OWNER);

        StoreEntity store = StoreEntity.builder()
                .id(storeId)
                .storeName("2L의 가게")
                .storeContents("2L의 가게입니다.")
                .user(anotherUser)
                .status(StoreStatus.ENABLE)
                .build();

        when(AuthUser.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        //Then
        Assertions.assertThrows(NdException.class,
                () -> menuService.menuSave(storeId, "울면", "울면입니다.", 8000L));
    }

    @Test
    void 메뉴생성_폐업상태_예외처리() {
        //Given
        Long userId = 1L;
        Long storeId = 1L;

        UserEntity user = new UserEntity(userId, "test@test.com", "Dlehdrjs12!", "부산", UserRole.OWNER);

        StoreEntity store = StoreEntity.builder()
                .id(storeId)
                .storeName("폐업한 가게")
                .storeContents("폐업한 가게입니다.")
                .user(user)
                .status(StoreStatus.DISABLE)
                .build();

        when(AuthUser.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        //Then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> menuService.menuSave(storeId, "볶음밥", "볶음밥 입니다.", 8000L));
    }
}
