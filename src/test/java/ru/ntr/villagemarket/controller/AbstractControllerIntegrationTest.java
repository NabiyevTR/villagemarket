package ru.ntr.villagemarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import ru.ntr.villagemarket.VillagemarketApplicationTests;
import ru.ntr.villagemarket.model.repository.*;

public abstract class AbstractControllerIntegrationTest extends VillagemarketApplicationTests {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductRepository productRepository;

    @MockBean
    protected CartRepository cartRepository;

    @MockBean
    protected PriceRepository priceRepository;

    @MockBean
    protected OrderRepository orderRepository;

    @MockBean
    protected OrderStatusRepository orderStatusRepository;

    @MockBean
    protected OrderHistoryRepository orderHistoryRepository;

    @MockBean
    protected CategoryRepository categoryRepository;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected RoleRepository roleRepository;


}
