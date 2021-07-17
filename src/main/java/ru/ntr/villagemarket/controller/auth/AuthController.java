package ru.ntr.villagemarket.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.AuthDto;
import ru.ntr.villagemarket.model.dto.ResponseAuthDto;
import ru.ntr.villagemarket.model.service.SecurityService;
import ru.ntr.villagemarket.model.service.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseAuthDto auth(@RequestBody AuthDto authDto) {

        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getUsername(),
                            authDto.getPassword()
                    )
            );



        ResponseAuthDto responseAuthDto = new ResponseAuthDto();
        responseAuthDto.setUsername(authDto.getUsername());
        responseAuthDto.setAccessToken(securityService.createJwtToken(authenticate));
        responseAuthDto.setGrantedAuthorities( userService.loadUserByUsername(authDto.getUsername()).getAuthorities());

        return responseAuthDto;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;

    }
}
