package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleService roleService;

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
                .isBlocked(userDto.isBlocked())
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
                .isBlocked(user.isBlocked())
                .address(user.getAddress())
                .comments(user.getComments())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList()))
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


