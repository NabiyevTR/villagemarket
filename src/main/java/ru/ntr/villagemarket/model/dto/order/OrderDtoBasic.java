package ru.ntr.villagemarket.model.dto.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.ntr.villagemarket.model.helpers.ProductItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
public class OrderDtoBasic {

    private int id;
    private String username;
    private String status;
    private Date orderDate;
    private Date deliveryDate;
    private double totalPrice;


}
