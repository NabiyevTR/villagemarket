package ru.ntr.villagemarket.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.ntr.villagemarket.model.helpers.CartItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Data
@Builder

public class CartDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder.Default
    private List<CartItem> cart = new ArrayList<>();

}
