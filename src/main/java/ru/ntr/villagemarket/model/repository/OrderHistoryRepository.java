package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.OrderHistoryItem;


public interface OrderHistoryRepository extends JpaRepository<OrderHistoryItem, Integer> {
}
