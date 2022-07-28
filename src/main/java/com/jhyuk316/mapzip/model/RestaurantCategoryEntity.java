package com.jhyuk316.mapzip.model;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "restaurant_category")
public class RestaurantCategoryEntity {
    @Id
    @GeneratedValue
    @Column(name = "restaurant_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public RestaurantCategoryEntity(RestaurantEntity restaurant, CategoryEntity category) {
        this.restaurant = restaurant;
        this.category = category;
    }
}
