package ru.ntr.villagemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class VillagemarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(VillagemarketApplication.class, args);
    }


}
