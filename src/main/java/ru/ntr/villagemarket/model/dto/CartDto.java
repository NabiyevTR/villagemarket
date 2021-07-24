package ru.ntr.villagemarket.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.ntr.villagemarket.model.helpers.CartItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Data
@Builder
public class CartDto {

    @Builder.Default
    private List<CartItem> cart = new ArrayList<>();

}
