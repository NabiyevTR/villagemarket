package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Order;
import ru.ntr.villagemarket.model.entity.OrderStatus;
import ru.ntr.villagemarket.model.entity.User;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByStatusNot(OrderStatus orderStatus);

    void deleteOrdersByUser(User user);


}

