package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.entity.ProductCategory;
import ru.ntr.villagemarket.model.mapper.ProductMapper;
import ru.ntr.villagemarket.model.repository.CategoryRepository;
import ru.ntr.villagemarket.model.repository.PriceRepository;
import ru.ntr.villagemarket.model.repository.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        return productMapper.fromProducts(productRepository.findAll());
    }

    @Override
    public List<ProductDto> findAllAvailable() {
        return productMapper.fromProducts(productRepository.findAllByAvailableForSaleIsTrue());
    }

    @Override
    public List<ProductDto> findAllAvailableByCategory(String category) {
        return productMapper.fromProducts(
                productRepository.findAllByCategoriesContains(
                        categoryRepository.findFirstByName(category)
                )
        );
    }

    @Override
    public ProductDto findById(int id) {

        return productMapper.fromProduct(productRepository.findById(id).orElseThrow());
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = productRepository.save(productMapper.toProduct(productDto));

        priceRepository.save(Price.builder()
                .cost(productDto.getPrice())
                .productId(product.getId())
                .date(new Date())
                .build());
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
