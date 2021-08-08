package ru.ntr.villagemarket.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ntr.villagemarket.model.dto.user.*;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDto> findAll();

    List<CustomerBasicDto> findAllCustomers();

    UserDto findById(int id);

    CustomerFullDto findCustomerById(int id);

    void save(UserDto userDto);

    void regUser(NewUserDto newUserDto);

    void delete(int id);

    UserProfileDto getCurrentUserProfile();

    User getCurrentUser();


}
