package ru.ntr.villagemarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.ProductDto;
import ru.ntr.villagemarket.model.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public List<ProductDto> showProducts() {
        return productService.findAll();
    }


    @GetMapping("/{id}")
    public ProductDto showProduct(@PathVariable("id") int id) {
        return productService.findById(id);
    }


    @PostMapping()
    public ProductDto create(@RequestBody ProductDto productDto) {
        return productService.save(productDto);

    }

    @DeleteMapping("/{id}")
    public int deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
        return HttpStatus.OK.value();
    }
}
