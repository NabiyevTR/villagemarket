package ru.ntr.villagemarket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ntr.villagemarket.config.AppProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Transient
    private static final String IMG_CATALOG = AppProperties.IMG_CATALOG;
    @Transient
    private static final String DEFAULT_IMAGE = AppProperties.DEFAULT_IMAGE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "available_for_sale")
    private boolean availableForSale;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "product")
    private List<Price> prices;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            foreignKey = @ForeignKey(name = "fk_products_categories_categories")
    )
    List<ProductCategory> categories;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"),
            foreignKey = @ForeignKey(name = "fk_orders_products_orders")
    )
    List<Order> orders;

    public Double getPrice() {
        return prices.stream()
                .max(Comparator.comparing(Price::getDate))
                .map(Price::getCost)
                .orElse(0.0);
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderedProduct> orderedProducts;

}
