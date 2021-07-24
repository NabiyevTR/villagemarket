package ru.ntr.villagemarket.model.helpers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {
    private int id;
    private String name;
    private double price;
    private int quantity;
}
