package ru.ntr.villagemarket.controller.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ntr.villagemarket.controller.AbstractControllerIntegrationTest;
import ru.ntr.villagemarket.model.entity.Cart;
import ru.ntr.villagemarket.model.entity.Price;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Product product_1 = Product.builder()
                .id(1)
                .name("Apple")
                .prices(List.of(
                        Price.builder()
                                .id(1)
                                .cost(1.0)
                                .date(new Date())
                                .build()
                ))
                .build();

        Product product_2 = Product.builder()
                .id(2)
                .name("Lemon")
                .prices(List.of(
                        Price.builder()
                                .id(2)
                                .cost(2.0)
                                .date(new Date())
                                .build()
                ))
                .build();

        Cart cart = Cart.builder()
                .id(1)
                .products(List.of(product_1, product_2, product_1))
                .build();

        User user = User.builder()
                .id(1)
                .username("user_1")
                .cart(cart)
                .build();

        Mockito.reset(cartRepository, userRepository);

        Mockito.when(cartRepository.getById(cart.getId())).thenReturn(cart);
        Mockito.when(cartRepository.save(cart)).thenReturn(cart);

        Mockito.when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(user));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"CUSTOMER"})
    void getCartSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cart", hasSize(2)))
                .andExpect(jsonPath("$.cart[0].name", is("Apple")))
                .andExpect(jsonPath("$.cart[0].quantity", is(2)))
                .andExpect(jsonPath("$.cart[1].name", is("Lemon")))
                .andExpect(jsonPath("$.cart[1].quantity", is(1)));

    }


    @Test
    @WithMockUser(username = "spring", roles = {"CUSTOMER"})
    void overwriteCartSuccess() {
    }

    @Test
    @WithMockUser(username = "spring", roles = {"CUSTOMER"})
    void checkoutSuccess() {
    }
}