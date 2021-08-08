package ru.ntr.villagemarket.model.dto.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.ntr.villagemarket.model.helpers.ProductItem;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class OrderDto extends OrderBasicDto {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private String comments;
    private List<ProductItem> products;

    public OrderDto(OrderBasicDto orderBasicDto) {
        super(orderBasicDto);
    }
}
