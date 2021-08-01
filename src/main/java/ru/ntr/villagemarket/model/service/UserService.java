package ru.ntr.villagemarket.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> findAll();

    UserDto findById(int id);

    UserDto findCurrent();

    void save(UserDto userDto);

    void delete(int id);

    UserDto showCurrentUser();

    User getCurrentUser();
}
