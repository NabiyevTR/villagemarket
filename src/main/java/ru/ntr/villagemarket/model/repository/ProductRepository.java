package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ntr.villagemarket.model.entity.ProductCategory;
import ru.ntr.villagemarket.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByAvailableForSaleIsTrue();

    List<Product> findAllByCategoriesContains(ProductCategory productCategory);

}
