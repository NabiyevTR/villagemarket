package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ntr.villagemarket.model.entity.Order;
import ru.ntr.villagemarket.model.entity.OrderStatus;
import ru.ntr.villagemarket.model.entity.OrderedProduct;
import ru.ntr.villagemarket.model.entity.User;

import java.util.Date;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByStatusNot(OrderStatus orderStatus);

    void deleteOrdersByUser(User user);

    @Query(value = "select  o.orderDate from  Order o where :orderedProduct member o.products")
    Date findOrderDateByProduct(OrderedProduct orderedProduct);

}

