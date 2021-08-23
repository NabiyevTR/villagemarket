package ru.ntr.villagemarket.controller.cms;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ru.ntr.villagemarket.model.dto.order.OrderStatusChangeRequestDto;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.entity.*;
import ru.ntr.villagemarket.model.repository.OrderHistoryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CMSOrderControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        OrderStatus locatedOrderStatus = new OrderStatus();
        locatedOrderStatus.setId(1);
        locatedOrderStatus.setStatus("Located");

        OrderStatus confirmedOrderStatus = new OrderStatus();
        confirmedOrderStatus.setId(1);
        confirmedOrderStatus.setStatus("Confirmed");

        OrderStatus closedOrderStatus = new OrderStatus();
        closedOrderStatus.setId(1);
        closedOrderStatus.setStatus("Closed");

        Order order_1 = Order.builder()
                .id(1)
                .customerFirstName("customer_1")
                .customerLastName("customer_1")
                .orderDate(new Date())
                .customerAddress("customer_address_1")
                .customerEmail("customer_email_1")
                .customerPhoneNumber("customer_phone_number_1")
                .deliveryDate(new Date())
                .products(List.of(OrderedProduct.builder()
                        .id(1)
                        .price(1.0)
                        .product(Product.builder()
                                .id(1)
                                .name("Apple")
                                .build())
                        .build()))
                .user(User.builder()
                        .id(1)
                        .build())
                .status(locatedOrderStatus)
                .history(List.of(
                        OrderHistoryItem.builder()
                                .orderStatus(locatedOrderStatus)
                                .date(new Date())
                                .order(Order.builder()
                                        .id(1)
                                        .build())
                                .build()
                ))
                .build();

        Order order_2 = Order.builder()
                .id(2)
                .customerFirstName("customer_2")
                .customerLastName("customer_2")
                .orderDate(new Date())
                .customerAddress("customer_address_2")
                .customerEmail("customer_email_2")
                .customerPhoneNumber("customer_phone_number_2")
                .deliveryDate(new Date())
                .products(List.of(OrderedProduct.builder()
                        .id(2)
                        .price(2.0)
                        .product(Product.builder()
                                .id(2)
                                .name("Orange")
                                .build())
                        .build()))
                .user(User.builder()
                        .id(2)
                        .build())
                .status(closedOrderStatus)
                .history(List.of(
                        OrderHistoryItem.builder()
                                .orderStatus(locatedOrderStatus)
                                .date(new Date())
                                .order(Order.builder()
                                        .id(2)
                                        .build())
                                .build(),
                        OrderHistoryItem.builder()
                                .orderStatus(closedOrderStatus)
                                .date(new Date())
                                .order(Order.builder()
                                        .id(3)
                                        .build())
                                .build()
                ))
                .build();


        var orders = List.of(order_1, order_2);

        Mockito.reset(orderRepository, orderRepository, orderHistoryRepository);
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        Mockito.when(orderRepository.findAllByStatusNot(closedOrderStatus)).thenReturn(
                orders.stream()
                        .filter(order -> !order.getStatus().getStatus().equals(closedOrderStatus.getStatus()))
                        .collect(Collectors.toList())
        );

        Mockito.when(orderRepository.findById(order_1.getId())).thenReturn(Optional.of(order_1));
        Mockito.when(orderRepository.findById(order_2.getId())).thenReturn(Optional.of(order_2));

        Mockito.when(orderStatusRepository.findByStatus(locatedOrderStatus.getStatus())).thenReturn(locatedOrderStatus);
        Mockito.when(orderStatusRepository.findByStatus(confirmedOrderStatus.getStatus())).thenReturn(confirmedOrderStatus);
        Mockito.when(orderStatusRepository.findByStatus(closedOrderStatus.getStatus())).thenReturn(closedOrderStatus);

        Mockito.when(orderStatusRepository.save(Mockito.any(OrderStatus.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(orderHistoryRepository.save(Mockito.any(OrderHistoryItem.class)))
                .thenAnswer(i -> i.getArguments()[0]);


    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showOrdersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/order")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showActiveOrdersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/order/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void showOrderFullInfoSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/cms/order/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void updateOrderStatusSuccess() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        var orderStatusChangeRequestDto = OrderStatusChangeRequestDto.builder()
                .statusId(10)
                .status("Confirmed")
                .build();

        mockMvc.perform(patch("/api/cms/order/{id}/status", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(orderStatusChangeRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("Confirmed")));

    }

    @Test
    @WithMockUser(username = "spring", roles = {"MANAGER"})
    void deleteOrderSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/cms/order/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}