package ru.ntr.villagemarket.controller.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.ntr.villagemarket.controller.AbstractControllerIntegrationTest;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.entity.ProductCategory;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


class ProductControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    public void setUp() {

        ProductCategory category_1 = ProductCategory.builder()
                .id(1)
                .name("fruits")
                .build();

        ProductCategory category_2 = ProductCategory.builder()
                .id(2)
                .name("vegetables")
                .build();

        List<ProductCategory> categories =
                new ArrayList<>(Arrays.asList(category_1, category_2));

        Product product_1 = Product.builder()
                .id(1)
                .name("apple")
                .availableForSale(true)
                .prices(
                        Collections.singletonList(Price.builder()
                                .id(1)
                                .cost(1.23)
                                .build())
                )
                .categories(Collections.singletonList(category_2))
                .build();

        Product product_2 = Product.builder()
                .id(2)
                .name("cucumber")
                .availableForSale(true)
                .prices(
                        Collections.singletonList(Price.builder()
                                .id(2)
                                .cost(1.33)
                                .build())

                )
                .categories(Collections.singletonList(category_2))
                .build();

        List<Product> products =
                new ArrayList<>(Arrays.asList(product_1, product_2));


        Mockito.reset(productRepository, categoryRepository);

        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        Mockito.when(productRepository.findById(product_2.getId())).thenReturn(Optional.of(product_2));
        Mockito.when(productRepository.findAllByAvailableForSaleIsTrue()).thenReturn(products);
        Mockito.when(productRepository.findAllByCategoriesContains(category_2)).thenReturn(List.of(product_2));

        Mockito.when(categoryRepository.findFirstByName(category_1.getName())).thenReturn(category_1);
        Mockito.when(categoryRepository.findFirstByName(category_2.getName())).thenReturn(category_2);
    }


    @Test
    void getAllAvailableProductsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("apple")))
                .andExpect(jsonPath("$[1].name", is("cucumber")));
    }

    @Test
    void getAllProductByIdSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getAllProductByCategory() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/product/category/vegetables")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)));
    }
}