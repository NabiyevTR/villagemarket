package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.config.AppProperties;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.entity.ProductCategory;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    public Product toProduct(ProductDto productDto) {

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .categories(productDto.getCategories().stream()
                        .map(categoryRepository::findFirstByName)
                        .collect(Collectors.toList()))
                .description(productDto.getDescription())
                .imgLink(productDto.getImgLink() == null || productDto.getImgLink().isBlank()
                        ? AppProperties.IMG_CATALOG + AppProperties.DEFAULT_IMAGE
                        : productDto.getImgLink())
                .availableForSale(productDto.isAvailableForSale())
                .build();

    }

    public ProductDto fromProduct(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categories(product.getCategories().stream()
                        .map(ProductCategory::getName)
                        .collect(Collectors.toList()))
                .price(product.getPrice())
                .description(product.getDescription())
                .imgLink(product.getImgLink() == null || product.getImgLink().isBlank()
                        ? AppProperties.IMG_CATALOG + AppProperties.DEFAULT_IMAGE
                        : product.getImgLink())
                .availableForSale(product.isAvailableForSale())
                .build();
    }

    public List<Product> toProducts(List<ProductDto> productDtoList) {
        return productDtoList.stream()
                .map(this::toProduct)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<ProductDto> fromProducts(List<Product> productList) {
        return productList.stream()
                .map(this::fromProduct)
                .sorted(Comparator.comparing(ProductDto::getName))
                .collect(Collectors.toList());
    }

}
