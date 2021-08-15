package ru.ntr.villagemarket.model.repository;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/*

@RunWith(SpringRunner.class)*/
@DataJpaTest(properties = {
        "spring.main.web-application-type=none",
        "spring.liquibase.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:vm;DB_CLOSE_DELAY=-1;",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.hibernate.ddl-auto=create"

})
public abstract class AbstractRepositoryTest {

    @Autowired
    protected TestEntityManager entityManager;
}
