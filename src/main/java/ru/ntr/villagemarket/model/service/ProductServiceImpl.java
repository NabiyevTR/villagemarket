package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.exceptions.NoSuchProductException;
import ru.ntr.villagemarket.model.dto.product.FullInfoProductDto;
import ru.ntr.villagemarket.model.dto.product.LastMonthSalesProductDto;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.entity.OrderedProduct;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.mapper.ProductMapper;
import ru.ntr.villagemarket.model.repository.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final CategoryRepository categoryRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;

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
    public FullInfoProductDto findFullInfoById(int id) {

        return productMapper.toManagerProductDto(productRepository.findById(id)
                        .orElseThrow(() -> new NoSuchProductException(id)));

    }

    @Override
    public ProductDto findById(int id) {
        return productMapper.fromProduct(productRepository.findById(id)
                .orElseThrow(() -> new NoSuchProductException(id)));
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
    public LastMonthSalesProductDto getLastMonthSales(int productId) {

        TreeMap<LocalDate, Double> sales = new TreeMap<>();
        LocalDate today = LocalDate.now();
        final int LAST_30_DAYS = 30;

        List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByProductId(productId);

        for (int i = 1; i <= LAST_30_DAYS; i++) {


            LocalDate date = today.minusDays(i);
            double totalSalesPerDay = 0;


            try {
                totalSalesPerDay = orderedProducts.stream()
                        .filter((orderedProduct )-> {
                            Date orderDate = orderRepository.findOrderDateByProduct(orderedProduct);
                            if (orderDate != null) {
                                LocalDate localOrderDate = LocalDate.ofInstant(orderDate.toInstant(), ZoneId.systemDefault());
                                return localOrderDate.equals(date);
                            }
                            return false;
                        })
                                .map(OrderedProduct::getPrice)
                                .reduce(0.0, Double::sum);
            } catch (Exception e) {
            }
            sales.put(date, totalSalesPerDay);
        }

        return LastMonthSalesProductDto.builder()
                .lastMonthSales(sales)
                .build();
    }


    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
