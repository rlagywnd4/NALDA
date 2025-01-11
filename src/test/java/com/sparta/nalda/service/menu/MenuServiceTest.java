package com.sparta.nalda.service.menu;


import com.sparta.nalda.entity.MenuEntity;
import com.sparta.nalda.entity.StoreEntity;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.MenuRepository;
import com.sparta.nalda.repository.StoreRepository;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.AuthUser;
import com.sparta.nalda.util.StoreStatus;
import com.sparta.nalda.util.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
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
                "test@test.com",
                "Dlehdrjs12!",
                "address",
                UserRole.OWNER
        );

        UserEntity savedUser = userRepository.save(user);
        mockAuthUser.when(AuthUser::getId).thenReturn(savedUser.getId());
    }

    @Test
    void 메뉴생성 () {
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
}
