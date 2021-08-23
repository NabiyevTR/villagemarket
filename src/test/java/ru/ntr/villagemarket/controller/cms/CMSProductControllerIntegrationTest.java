package ru.ntr.villagemarket.controller.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ntr.villagemarket.controller.AbstractControllerIntegrationTest;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.entity.ProductCategory;


import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CMSProductControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Mockito.reset(userRepository);

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
                .prices(
                        Collections.singletonList(Price.builder()
                                .id(1)
                                .cost(1.23)
                                .build())
                )
                .availableForSale(false)
                .categories(Collections.singletonList(category_2))
                .build();

        Product product_2 = Product.builder()
                .id(2)
                .name("cucumber")
                .prices(
                        Collections.singletonList(Price.builder()
                                .id(2)
                                .cost(1.33)
                                .build())

                )
                .availableForSale(true)
                .categories(Collections.singletonList(category_2))
                .build();

        List<Product> products =
                new ArrayList<>(Arrays.asList(product_1, product_2));

        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productRepository.findAllByAvailableForSaleIsTrue()).thenReturn(products.stream()
                .filter(Product::isAvailableForSale)
                .sorted(Comparator.comparingInt(Product::getId))
                .collect(Collectors.toList()));

        Mockito.when(productRepository.findById(product_1.getId())).thenReturn(Optional.of(product_1));
        Mockito.when(productRepository.findById(product_2.getId())).thenReturn(Optional.of(product_2));

        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(priceRepository.save(Mockito.any(Price.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showProductsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("apple")))
                .andExpect(jsonPath("$[1].name", is("cucumber")));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showAvailableProductsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/product/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("cucumber")));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showProductSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/product/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("cucumber")));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void createSuccess() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        ProductDto productDto = ProductDto.builder()
                .id(3)
                .name("Fig")
                .price(2.44)
                .availableForSale(true)
                .categories(List.of("Fruits"))
                .build();

        mockMvc.perform(post("/api/cms/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(productDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void updateProductSuccess() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        ProductDto productDto = ProductDto.builder()
                .id(2)
                .name("Fig")
                .price(2.44)
                .availableForSale(true)
                .categories(List.of("Fruits"))
                .build();



        mockMvc.perform(patch("/api/cms/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(productDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void deleteProductSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/cms/product/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}