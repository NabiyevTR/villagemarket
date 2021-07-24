package ru.ntr.villagemarket.model.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.entity.User;

import java.util.*;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    private final  User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(ROLE_PREFIX + r))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !user.isBlocked();
    }

    public List<String> getRoles() {
        return user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
    }

}

