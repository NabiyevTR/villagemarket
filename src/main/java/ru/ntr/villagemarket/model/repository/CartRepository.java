package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Cart;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}
