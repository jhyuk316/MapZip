package com.jhyuk316.mapzip.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.jhyuk316.mapzip.model.RestaurantEntity;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
    List<RestaurantEntity> findByRestaurantname(String restaurantName);

    List<RestaurantEntity> findByRestaurantnameContains(String restaurantName);
}
