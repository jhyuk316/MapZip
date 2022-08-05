package com.jhyuk316.mapzip.model;

import javax.persistence.*;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private final List<RestaurantCategoryEntity> restaurantCategories = new ArrayList<>();

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity(CategoryDTO categoryDTO) {
        this.name = categoryDTO.getName();
    }

    public void addRestaurantCategory(RestaurantCategoryEntity restaurantCategory) {
        this.restaurantCategories.add(restaurantCategory);
    }
}
