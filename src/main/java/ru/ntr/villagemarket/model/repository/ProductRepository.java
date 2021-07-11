package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
