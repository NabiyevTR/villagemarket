package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Price;


public interface PriceRepository extends JpaRepository<Price, Integer> {
}
