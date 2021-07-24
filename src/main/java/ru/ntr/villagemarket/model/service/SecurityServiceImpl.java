package ru.ntr.villagemarket.model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.config.AppProperties;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;


@Service
public class SecurityServiceImpl implements SecurityService {

    private final KeyPair keyPair;
    private final AppProperties appProperties;

    public SecurityServiceImpl(AppProperties appProperties) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        this.appProperties = appProperties;
        keyPair =appProperties.getKeyPair();
    }


    @Override
    public String verifyJwt(String jwt) {

        final Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(jwt);

        return claimsJws.getBody().getIssuer();
    }

    @Override
    public String createJwtToken(Authentication authenticate) {
        if (authenticate.isAuthenticated()) {
            return Jwts.builder()
                    .setIssuer(authenticate.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 3_600_000_00))
                    .claim("name", authenticate.getName())
                    .signWith(keyPair.getPrivate())
                    .compact();
        } else {
            throw new BadCredentialsException("Login and password invalid");
        }
    }
}
