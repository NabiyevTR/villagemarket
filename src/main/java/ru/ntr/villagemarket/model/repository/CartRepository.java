package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ntr.villagemarket.model.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Modifying
    @Query("delete from Cart c where c.id=:id")
    void deleteById(@Param("id") int id);
}
