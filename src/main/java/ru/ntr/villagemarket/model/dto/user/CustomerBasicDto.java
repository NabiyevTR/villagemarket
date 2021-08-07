package ru.ntr.villagemarket.model.dto.user;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class CustomerBasicDto {

    private int id;
    private String username;
    private Date regDate;
    private boolean isBlocked;
    private String status;
    private int totalOrders;
    private double totalPrice;

    protected CustomerBasicDto(CustomerBasicDto customerBasicDto) {
        this.id = customerBasicDto.getId();
        this.username = customerBasicDto.getUsername();
        this.regDate = customerBasicDto.getRegDate();
        this.isBlocked = customerBasicDto.isBlocked();
        this.status = customerBasicDto.getStatus();
        this.totalOrders = customerBasicDto.getTotalOrders();
        this.totalPrice = customerBasicDto.getTotalPrice();
    }
}
