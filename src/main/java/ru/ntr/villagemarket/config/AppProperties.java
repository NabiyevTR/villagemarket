package ru.ntr.villagemarket.config;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class AppProperties {

    @Getter
    private KeyPair keyPair;

    @Value("${market.publicKeyUrl}")
    private String publicKeyUrl;

    @Value("${market.privateKeyUrl}")
    private String privateKeyUrl;

    @Value("${market.imgCatalog}")
    public String imgCatalog;

    @Value("${market.defaultImage}")
    public String defaultImage;

    @Value("${market.daysForDelivery}")
    public int daysForDelivery;

    @Value("${market.controllerLogging}")
    public boolean controllerLogging;

    @SneakyThrows
    @PostConstruct
    private void init() {
        loadKeyPair();
    }

    private PublicKey loadPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        URL url = new URL(publicKeyUrl);
        String publicKeyContent = new String(url.openStream().readAllBytes());

        publicKeyContent = publicKeyContent
                .replaceAll("\\r", "")
                .replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        PublicKey pl = kf.generatePublic(keySpecX509);
        return kf.generatePublic(keySpecX509);
    }

    private PrivateKey loadPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        URL url = new URL(privateKeyUrl);
        String privateKeyContent = new String(url.openStream().readAllBytes());

        privateKeyContent = privateKeyContent
                .replaceAll("\\r", "")
                .replaceAll("\\n", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        return kf.generatePrivate(keySpecPKCS8);
    }


    public void loadKeyPair() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        keyPair = new KeyPair(loadPublicKey(), loadPrivateKey());
    }

}

