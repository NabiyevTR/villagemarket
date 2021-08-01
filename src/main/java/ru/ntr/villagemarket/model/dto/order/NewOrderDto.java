package ru.ntr.villagemarket.model.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class NewOrderDto {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private Date deliveryDate;
    private String comments;

}
