package ru.ntr.villagemarket;


import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import ru.ntr.villagemarket.model.entity.ProductCategory;
import ru.ntr.villagemarket.model.repository.CategoryRepository;
import ru.ntr.villagemarket.model.repository.ProductRepository;
import ru.ntr.villagemarket.model.repository.RoleRepository;
import ru.ntr.villagemarket.model.repository.UserRepository;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.jpa.show-sql=false"})
public class VillagemarketApplicationTests {

    @Autowired
    protected WebApplicationContext context;




}
