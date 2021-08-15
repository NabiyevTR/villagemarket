package ru.ntr.villagemarket.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ntr.villagemarket.model.entity.User;

import static org.assertj.core.api.Assertions.assertThat;


class UserRepositoryTest extends AbstractRepositoryTest  {


    @Autowired
    private UserRepository userRepository;

    @Test
    void userSaveRepositoryTest() {

        User user = User.builder()
                .username("user_1")
                .password("123456")
                .email("mail_1@mail.com")
                .phoneNumber("1")
                .isBlocked(false)
                .build();


        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findUserByUsername(user.getUsername()).orElseThrow();
        System.out.println(user);
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }
}