package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.info("Loading user: {}", username);

        User user = repository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", username);

                    return new UsernameNotFoundException(
                            "User not found: " + username
                    );
                });

        log.info("User loaded successfully: {}", username);

        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + role.getName().toUpperCase()
                                )
                        ),
                        role.getPermissions()
                                .stream()
                                .map(permission ->
                                        new SimpleGrantedAuthority(
                                                permission.getName()
                                        )
                                )
                ))
                .toList();

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
