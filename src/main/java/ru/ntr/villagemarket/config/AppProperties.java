package ru.ntr.villagemarket.config;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

@Slf4j
@Component
public final class AppProperties {


    @Getter
    private Properties properties;

    @Getter
    private KeyPair keyPair;

    private static final String RESOURCE_PATH = new File("src/main/resources").getAbsolutePath();
    private static final String APP_CONFIG_PATH = "application.properties";
    private static final String PUBLIC_KEY_PATH = "/keypair/id_rsa_villagemarket.pub";
    private static final String PRIVATE_KEY_PATH = "/keypair/id_rsa_villagemarket";

    public static String imgCatalog = "http://bimel.ru/content/vm/catalog/";
    public static String defaultImage = "no-product-image.png";

    public static int daysForDelivery = 2;
    public static boolean controllerLogging = false;


    @SneakyThrows
    private AppProperties() {

        loadProperties();

        loadKeyPair();

    }

    private void loadProperties() {

        try {
            properties = new Properties();
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(APP_CONFIG_PATH));

            imgCatalog = properties.getProperty("market.imgCatalog");
            defaultImage = properties.getProperty("market.defaultImage");
            daysForDelivery = Integer.parseInt(properties.getProperty("market.daysForDelivery"));
            controllerLogging = Boolean.parseBoolean(properties.getProperty("market.controllerLogging"));

        } catch (IOException e) {
            log.error("Cannot load properties. Server is running with default properties", e.getMessage());

        }
    }

    private PublicKey loadPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {

        String publicKeyContent = new String(Files.readAllBytes(Paths.get(RESOURCE_PATH, PUBLIC_KEY_PATH)));
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

        String privateKeyContent = new String(Files.readAllBytes(Paths.get(RESOURCE_PATH, PRIVATE_KEY_PATH)));
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

