package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.ntr.villagemarket.model.entity.OrderedProduct;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {

    @Query(value = "select  op from  OrderedProduct op where op.product.id=:productId")
    List<OrderedProduct> findAllByProductId(int productId);

}
