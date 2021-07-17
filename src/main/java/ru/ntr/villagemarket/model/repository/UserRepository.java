package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String login);

    User findUserByEmail(String email);

    User findUserByPhoneNumber(String phoneNumber);

    User findUserById(int id);

}
