package ru.ntr.villagemarket.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String customerFirstName;

    @Column(name = "last_name")
    private String customerLastName;

    @Column(name = "address")
    private String customerAddress;

    @Column(name = "email")
    private String customerEmail;

    @Column(name = "phone_number")
    private String customerPhoneNumber;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"),
            foreignKey = @ForeignKey(name = "fk_orders_products_ordered_products")
    )
    private List<OrderedProduct> products;

    @ToString.Exclude
    @OneToMany(mappedBy="order", fetch = FetchType.LAZY)
    private List<OrderHistoryItem> history;



}
