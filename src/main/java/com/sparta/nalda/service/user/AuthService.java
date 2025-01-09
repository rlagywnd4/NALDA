package com.sparta.nalda.service.user;

import com.sparta.nalda.dto.user.NaldaUserDetails;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.TokenProvider;
import com.sparta.nalda.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public void signup(String email, String password, String address, UserRole userRole) {

        if(userRepository.existsByEmail(email)){
           throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String hashPassword = passwordEncoder.encode(password);
        UserEntity user = new UserEntity(email, hashPassword, address, userRole);
        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new NaldaUserDetails(user);
    }
}
