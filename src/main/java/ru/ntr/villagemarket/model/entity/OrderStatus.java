package ru.ntr.villagemarket.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "orders_status")
@Data
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status")
    private int text;
}
