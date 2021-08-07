package ru.ntr.villagemarket.model.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.user.UserProfileDto;
import ru.ntr.villagemarket.model.entity.User;



@Component
public class UserProfileMapper {

    private final OrderMapper orderMapper;

    public UserProfileMapper(@Lazy OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public UserProfileDto fromUser(User user) {

        return UserProfileDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .orders(orderMapper.toOrderDtos(user.getOrders()))
                .build();
    }

}


