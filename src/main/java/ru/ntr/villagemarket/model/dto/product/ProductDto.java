package ru.ntr.villagemarket.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ntr.villagemarket.config.AppProperties;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imgLink;
    private boolean availableForSale;
    private List<String> categories;
}
