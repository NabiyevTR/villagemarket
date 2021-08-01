package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.user.UserProfileDto;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.service.UserService;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    private final UserService userService;

    public User toUser(UserProfileDto userProfileDto) {

        return User.builder()
                .id(userService.getCurrentUser().getId())
                .password(userProfileDto.getPassword())
                .firstName(userProfileDto.getFirstName())
                .lastName(userProfileDto.getLastName())
                .birthDate(userProfileDto.getBirthDate())
                .gender((int) userProfileDto.getGender() == 0 ? 'u' : userProfileDto.getGender())
                .address(userProfileDto.getAddress())
                .phoneNumber(userProfileDto.getPhoneNumber())
                .email(userProfileDto.getEmail())
                .build();
    }

    public UserProfileDto fromUser(User user) {

        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();

    }


}


