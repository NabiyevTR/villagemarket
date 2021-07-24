package ru.ntr.villagemarket.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.ntr.villagemarket.model.helpers.CartItem;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderDto {

    private int id;
    @Builder.Default
    private String firstName = "";
    @Builder.Default
    private String lastName = "";
    @Builder.Default
    private String address = "";
    @Builder.Default
    private String phoneNumber = "";
    @Builder.Default()
    private String email = "";
    private int status;

    //TODO add comments

    @Builder.Default
    private List<CartItem> cart = new ArrayList<>();


}
