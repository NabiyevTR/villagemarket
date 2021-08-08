package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserById(int id);

    List<User> findUsersByRolesContains(Role role);



}
