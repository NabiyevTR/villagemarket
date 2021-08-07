package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.product.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    List<ProductDto> findAllAvailable();

    List<ProductDto> findAllAvailableByCategory(String category);

    ProductDto findById(int id);

    void save(ProductDto productDto);

    void delete(int id);
}
