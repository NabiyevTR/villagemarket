package ru.ntr.villagemarket.model.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ntr.villagemarket.model.dto.ProductDto;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ProductMapper {

    public static Product toProduct(ProductDto productDto) {

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .build();

    }

    public static ProductDto fromProduct(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public static List<Product> toProducts(List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(ProductMapper::toProduct)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public static List<ProductDto> fromProducts(List<Product> productList) {
        return productList.stream()
                .map(ProductMapper::fromProduct)
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList());
    }

}
