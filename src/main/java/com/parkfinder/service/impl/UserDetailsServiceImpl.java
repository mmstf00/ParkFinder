package com.parkfinder.service.impl;

import com.parkfinder.entity.User;
import com.parkfinder.model.CustomUserDetails;
import com.parkfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    /**
     * Actual implementation is to return by email not username.
     *
     * @param email email to be checked
     * @return UserDetails object
     * @throws UsernameNotFoundException if user is not found by email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findUserByEmail(email);
        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("email not found " + email));

    }
}
