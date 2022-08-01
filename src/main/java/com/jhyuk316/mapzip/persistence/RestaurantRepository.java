package com.jhyuk316.mapzip.persistence;

import com.jhyuk316.mapzip.model.CategoryEntity;
import com.jhyuk316.mapzip.model.RestaurantEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findById(Long id);

    Optional<RestaurantEntity> findByName(String name);

    List<RestaurantEntity> findByNameContains(String name);

    List<RestaurantEntity> findByAddress(String Address);

    List<RestaurantEntity> findByLatitudeBetweenAndLongitudeBetween(
            double minLatitute, double maxLatitute,
            double minlongitude, double maxlongitude);

    Page<RestaurantEntity> findAll(Pageable pageable);

    @Query("select r " +
            "from RestaurantEntity r " +
            "join fetch r.restaurantCategories rc " +
            "join fetch rc.category c " +
            "where r.id = :id")
    Optional<RestaurantEntity> findByIdWithCategory(@Param("id") Long id);

    @Query("select r " +
            "from RestaurantEntity r " +
            "join fetch r.restaurantCategories rc " +
            "join fetch rc.category c " +
            "where r.name = :name")
    Optional<RestaurantEntity> findByNameWithCategory(@Param("name") String name);

}
