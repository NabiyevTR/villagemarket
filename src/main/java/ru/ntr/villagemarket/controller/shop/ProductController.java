package ru.ntr.villagemarket.controller.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public List<ProductDto> showAvailableProducts() {
        return productService.findAllAvailable();
    }

    @GetMapping("/category/{category}")
    public List<ProductDto> showAvailableProductsByCategory(@PathVariable("category") String category) {
        return productService.findAllAvailableByCategory(category);
    }

    @GetMapping("/{id}")
    public ProductDto showProduct(@PathVariable("id") int id) {
        return productService.findById(id);
    }

}
