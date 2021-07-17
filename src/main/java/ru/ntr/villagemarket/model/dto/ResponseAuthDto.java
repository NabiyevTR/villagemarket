package ru.ntr.villagemarket.model.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class ResponseAuthDto {
    private String username;
    private String accessToken;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

}
