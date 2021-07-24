package ru.ntr.villagemarket.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ntr.villagemarket.model.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> findAll();

    UserDto findById(int id);

    UserDto findCurrent();

    void save(UserDto userDto);

    void delete(int id);


}
