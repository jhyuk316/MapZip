package com.jhyuk316.mapzip.persistence;

import com.jhyuk316.mapzip.model.CategoryEntity;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.jhyuk316.mapzip.model.RestaurantCategoryEntity;

@Repository
public interface RestaurantCategoryRepository
        extends JpaRepository<RestaurantCategoryEntity, Long> {

    Optional<RestaurantCategoryEntity> findByRestaurantAndCategory(RestaurantEntity restaurant, CategoryEntity category);
    

}
