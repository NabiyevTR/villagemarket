package ru.ntr.villagemarket.model.dto.user;

import lombok.Data;

import lombok.experimental.SuperBuilder;
import ru.ntr.villagemarket.model.dto.order.OrderDto;

import java.util.List;

@Data
@SuperBuilder
public class CustomerFullDto extends CustomerBasicDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String comments;
    private List<OrderDto> orders;

    public CustomerFullDto(CustomerBasicDto customerBasicDto) {
        super(customerBasicDto);
    }
}
