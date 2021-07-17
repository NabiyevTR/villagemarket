package ru.ntr.villagemarket.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassBCrypt {

    public static void main(String[] args) {
        //Safe pass for SUPER ADMIN
        final String pass = "123456";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(
                encoder.encode(pass)
        );

    }


}
