package ru.ntr.villagemarket.model.mapper;

import ru.ntr.villagemarket.model.dto.UserDto;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserDto userDto) {

        return User.builder()
                .id(userDto.getId())
                .username(userDto.getLogin())
                .password(userDto.getPassword())
                .roles(userDto.getRoles())
                .build();
    }

    public static UserDto fromUser(User user) {

        return UserDto.builder()
                .id(user.getId())
                .login(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

    public static List<User> toUsers(List<UserDto> userDtoList) {
        return userDtoList.stream()
                .map(UserMapper::toUser)
                .collect(Collectors.toList());
    }

    public static List<UserDto> fromUsers(List<User> userList) {
        return userList.stream()
                .map(UserMapper::fromUser)
                .collect(Collectors.toList());
    }

}


