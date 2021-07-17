package ru.ntr.villagemarket.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair();
        System.out.println(new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded())));
        System.out.println(new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded())));

    }
}
