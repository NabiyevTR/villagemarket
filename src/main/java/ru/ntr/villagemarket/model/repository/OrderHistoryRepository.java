package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.OrderHistory;


public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {
}
