package com.dsm.umjunsik.security;

import com.dsm.umjunsik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findUserById(id)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username:" + id + "not found."));
    }

}

