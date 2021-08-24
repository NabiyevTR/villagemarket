package ru.ntr.villagemarket.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

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

    public Double getPrice() {
        return prices.stream()
                .max(Comparator.comparing(Price::getDate))
                .map(Price::getCost)
                .orElse(0.0);
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderedProduct> orderedProducts;

}
