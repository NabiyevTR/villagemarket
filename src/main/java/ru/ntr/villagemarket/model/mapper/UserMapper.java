package ru.ntr.villagemarket.model.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.user.NewUserDto;
import ru.ntr.villagemarket.model.dto.user.UpdateUserProfileDto;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.service.RoleService;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;

    public UserMapper(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    public User toUser(UserDto userDto) {

        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .regDate(userDto.getRegDate())
                .gender((int) userDto.getGender() == 0 ? 'u' : userDto.getGender())
                .isBlocked(!userDto.isActive())
                .address(userDto.getAddress())
                .comments(userDto.getComments())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .roles(userDto.getRoles().stream()
                        .map(roleService::getRoleByName)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public User toUser(NewUserDto newUserDto) {

        return User.builder()
                .username(newUserDto.getUsername())
                .password(bCryptPasswordEncoder.encode(newUserDto.getPassword()))
                .firstName(newUserDto.getFirstName())
                .lastName(newUserDto.getLastName())
                .birthDate(newUserDto.getBirthDate())
                .regDate(new Date())
                .gender((int) newUserDto.getGender() == 0 ? 'u' : newUserDto.getGender())
                .isBlocked(!newUserDto.isActive())
                .address(newUserDto.getAddress())
                .comments("")
                .phoneNumber(newUserDto.getPhoneNumber())
                .email(newUserDto.getEmail())
                .roles(newUserDto.getRoles() == null || newUserDto.getRoles().isEmpty()
                        ? Arrays.asList(roleService.getRoleByName("CUSTOMER"))
                        : newUserDto.getRoles().stream()
                        .map(roleService::getRoleByName)
                        .collect(Collectors.toList()))
                .build();
    }

    public User toUser(UpdateUserProfileDto updateUserProfileDto) {

        return User.builder()
                .firstName(updateUserProfileDto.getFirstName())
                .lastName(updateUserProfileDto.getLastName())
                .birthDate(updateUserProfileDto.getBirthDate())
                .gender((int) updateUserProfileDto.getGender() == 0 ? 'u' : updateUserProfileDto.getGender())
                .address(updateUserProfileDto.getAddress())
                .phoneNumber(updateUserProfileDto.getPhoneNumber())
                .email(updateUserProfileDto.getEmail())
                .build();
    }

    public UserDto fromUser(User user) {

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .regDate(user.getRegDate())
                .gender(user.getGender())
                .active(!user.isBlocked())
                .address(user.getAddress())
                .comments(user.getComments())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(Role::getRole)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<User> toUsers(List<UserDto> userDtoList) {
        return userDtoList.stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> fromUsers(List<User> userList) {
        return userList.stream()
                .map(this::fromUser)
                .collect(Collectors.toList());
    }

}


