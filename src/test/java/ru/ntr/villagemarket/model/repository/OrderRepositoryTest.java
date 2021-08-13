package ru.ntr.villagemarket.model.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ntr.villagemarket.model.entity.Order;
import ru.ntr.villagemarket.model.entity.OrderStatus;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();


    private OrderStatus locatedOrderStatus;
    private OrderStatus closedOrderStatus;
    private Order order_1;
    private Order order_2;
    private User user_1;
    private User user_2;

    @BeforeEach
    void setup() {

        locatedOrderStatus = new OrderStatus();
        locatedOrderStatus.setStatus("Located");

        closedOrderStatus = new OrderStatus();
        closedOrderStatus.setStatus("Closed");

        user_1 = User.builder()
                .username("user_1")
                .build();

        user_2 = User.builder()
                .username("user_2")
                .build();


        order_1 = Order.builder()
                .comments("order_1")
                .status(locatedOrderStatus)
                .user(user_1)
                .build();


        order_2 = Order.builder()
                .comments("order_2")
                .status(closedOrderStatus)
                .user(user_2)
                .build();

        entityManager.persist(locatedOrderStatus);
        entityManager.persist(closedOrderStatus);
        entityManager.persist(user_1);
        entityManager.persist(user_2);
        entityManager.persist(order_1);
        entityManager.persist(order_2);
        entityManager.flush();
    }


    @Test
    void findAllByStatusNotRepositoryTest() throws JsonProcessingException {

        List<Order> found = orderRepository.findAllByStatusNot(closedOrderStatus);
        System.out.println( ow.writeValueAsString(found));
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getComments()).isEqualTo("order_1");
    }

    @Test
    void deleteOrdersByUserRepositoryTest() throws JsonProcessingException {
        orderRepository.deleteOrdersByUser(user_1);
        List<Order> found = orderRepository.findAll();
        System.out.println( ow.writeValueAsString(found));
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getComments()).isEqualTo("order_2");

    }
}