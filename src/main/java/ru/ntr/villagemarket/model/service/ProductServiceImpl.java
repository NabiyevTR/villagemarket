package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.ProductDto;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.mapper.ProductMapper;
import ru.ntr.villagemarket.model.repository.PriceRepository;
import ru.ntr.villagemarket.model.repository.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;

    @Override
    public List<ProductDto> findAll() {
        return ProductMapper.fromProducts(productRepository.findAll());
    }

    @Override
    public ProductDto findById(int id) {

        return ProductMapper.fromProduct(productRepository.findById(id).orElseThrow());
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = productRepository.save(ProductMapper.toProduct(productDto));

        priceRepository.save(Price.builder()
                .cost(productDto.getPrice())
                .productId(product.getId())
                .date(new Date())
                .build());

        return productDto;
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
