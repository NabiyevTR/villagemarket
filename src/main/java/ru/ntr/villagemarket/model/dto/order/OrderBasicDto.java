package ru.ntr.villagemarket.model.dto.order;


import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Data
@SuperBuilder
public class OrderBasicDto {

    private int id;
    private String username;
    private String status;
    private Date orderDate;
    private Date deliveryDate;
    private double totalPrice;

    protected OrderBasicDto(OrderBasicDto orderBasicDto) {
        this.id= orderBasicDto.getId();
        this.username= orderBasicDto.getUsername();
        this.status= orderBasicDto.getStatus();
        this.orderDate= orderBasicDto.getOrderDate();
        this.deliveryDate= orderBasicDto.getDeliveryDate();
        this.totalPrice = orderBasicDto.getTotalPrice();
    }


}
