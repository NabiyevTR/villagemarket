package ru.ntr.villagemarket.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullInfoProductDto {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imgLink;
    private boolean availableForSale;
    private List<String> categories;
   }
