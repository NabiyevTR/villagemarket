package ru.ntr.villagemarket.controller.cms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/cms/product")
@Slf4j
@RequiredArgsConstructor
@Secured({"ROLE_SUPERADMIN", "ROLE_MANAGER"})
public class CMSProductController {

    private final ProductService productService;

    @GetMapping()
    public List<ProductDto> showProducts() {
        return productService.findAll();
    }

    @GetMapping("available")
    public List<ProductDto> showAvailableProducts() {
        return productService.findAllAvailable();
    }

    @GetMapping("/{id}")
    public ProductDto showProduct(@PathVariable("id") int id) {
        return productService.findById(id);
    }

    @PostMapping("")
    public int create(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return HttpStatus.OK.value();
    }

    @PatchMapping(value = "/{id}/edit")
    public int updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") int id) {
        productService.save(productDto);
        return HttpStatus.OK.value();
    }

    @DeleteMapping("/{id}")
    public int deleteProduct(@PathVariable("id") int id) {
        productService.delete(id);
        return HttpStatus.OK.value();
    }
}
