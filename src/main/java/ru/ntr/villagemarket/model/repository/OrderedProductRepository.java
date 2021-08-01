package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.OrderedProduct;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {
}
