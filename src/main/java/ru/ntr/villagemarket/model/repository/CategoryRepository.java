package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.entity.ProductCategory;

import java.util.List;

public interface CategoryRepository extends JpaRepository<ProductCategory, Integer> {

    ProductCategory findFirstByName(String name);
}
