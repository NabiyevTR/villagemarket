package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(int id);

    void save(ProductDto productDto);

    void delete(int id);
}
