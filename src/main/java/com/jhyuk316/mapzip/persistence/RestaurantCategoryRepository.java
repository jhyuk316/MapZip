package com.jhyuk316.mapzip.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.jhyuk316.mapzip.model.RestaurantCategoryEntity;

@Repository
public interface RestaurantCategoryRepository
        extends JpaRepository<RestaurantCategoryEntity, Integer> {


}
