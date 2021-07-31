package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Order;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByStatusIsNotIn(String[] notActiveStats);

}
