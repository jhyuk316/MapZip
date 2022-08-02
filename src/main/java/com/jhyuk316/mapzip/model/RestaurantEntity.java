package com.jhyuk316.mapzip.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
// @Table(name = "Restaurant", indexes = @Index(name = "i_location", columnList = "latitude, longitude"))
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private long id;

    @NotNull
    private String name;

    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private String address;

    private String description;

    private float rating;

    @OneToMany(mappedBy = "restaurant")
    private final List<RestaurantCategoryEntity> restaurantCategories = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private final List<RestaurantYoutuberEntity> restaurantYoutubers = new ArrayList<>();

    @Builder
    public RestaurantEntity(long id, String name, double latitude, double longitude, String address, String description, float rating) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
        this.rating = rating;
    }

    public void addRestaurantCategory(RestaurantCategoryEntity restaurantCategory) {
        this.restaurantCategories.add(restaurantCategory);
    }

    public void addRestaurantYoutuber(RestaurantYoutuberEntity restaurantYoutuber) {
        this.restaurantYoutubers.add(restaurantYoutuber);
    }

}
