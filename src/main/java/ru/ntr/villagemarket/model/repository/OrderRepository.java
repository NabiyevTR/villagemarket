package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer> {
}
