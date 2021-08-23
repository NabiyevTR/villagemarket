package ru.ntr.villagemarket.model.service;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    String verifyJwt(String jwt);

    String createJwtToken(Authentication authenticate);

}
