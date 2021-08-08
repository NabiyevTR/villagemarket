package ru.ntr.villagemarket.model.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.user.CustomerBasicDto;
import ru.ntr.villagemarket.model.dto.user.CustomerFullDto;
import ru.ntr.villagemarket.model.entity.OrderedProduct;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    private final OrderMapper orderMapper;

    public CustomerMapper(@Lazy OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public CustomerBasicDto toCustomerBasicDto(User user) {
        return CustomerBasicDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .isBlocked(user.isBlocked())
                .regDate(user.getRegDate())
                .totalOrders(user.getOrders().size())
                .totalPrice(user.getOrders()
                        .stream()
                        .map(e -> e.getProducts().stream()
                                .map(OrderedProduct::getPrice)
                                .reduce(0.0, Double::sum)
                        )
                        .reduce(0.0, Double::sum)
                )
                .build();
    }

    public CustomerFullDto toCustomerFullDto(User user) {
        var customerFullDto = new CustomerFullDto(toCustomerBasicDto(user));
        customerFullDto.setAddress(user.getAddress());
        customerFullDto.setComments(user.getComments());
        customerFullDto.setEmail(user.getEmail());
        customerFullDto.setFirstName(user.getFirstName());
        customerFullDto.setLastName(user.getLastName());
        customerFullDto.setOrders(orderMapper.toOrderDtos(user.getOrders()));
        customerFullDto.setPhoneNumber(user.getPhoneNumber());
        return customerFullDto;
    }

    public List<CustomerBasicDto> toCustomerBasicDto(List<User> userList) {
        return userList.stream()
                .map(this::toCustomerBasicDto)
                .collect(Collectors.toList());
    }

}


