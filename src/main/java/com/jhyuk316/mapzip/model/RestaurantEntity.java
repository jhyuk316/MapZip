package com.jhyuk316.mapzip.model;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Restaurant", indexes = @Index(name="i_location", columnList = "latitude, longitude"))
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restaurant_id")
    private long id;

    private String name;

    private double latitude;
    private double longitude;
    private String address;

    private String description;

    @Column(nullable = true)
    private float rating;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantCategoryEntity> restaurantCategories = new ArrayList<>();

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

    public void addCategory(String category){

        RestaurantCategoryEntity restaurantCategory = new RestaurantCategoryEntity();
        restaurantCategories.add(restaurantCategory);
    }
}
