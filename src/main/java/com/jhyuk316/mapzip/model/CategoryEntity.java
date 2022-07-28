package com.jhyuk316.mapzip.model;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<RestaurantCategoryEntity> restaurantCategories = new ArrayList<>();

    public CategoryEntity(String name) {
        this.name = name;
    }

    public void addRestaurantCategory(RestaurantCategoryEntity restaurantCategory) {
        this.restaurantCategories.add(restaurantCategory);
    }
}
