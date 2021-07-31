package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.user.UserDto;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.repository.RoleRepository;

import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RegUserMapper {

    private final RoleRepository roleRepository;

    public User toUser(UserDto userDto) {

        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .regDate(new Date())
                .gender((int) userDto.getGender() == 0 ? 'u' : userDto.getGender())
                .isBlocked(false)
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .email(userDto.getEmail())
                .roles(List.of(roleRepository.findByRole("CUSTOMER")))
                .build();
    }
}


