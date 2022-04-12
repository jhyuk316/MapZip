package com.jhyuk316.mapzip.persistence;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    List<RestaurantEntity> findById(long id);

    List<RestaurantEntity> findByNameContains(String name);

    List<RestaurantEntity> findByLatitudeBetweenAndLongitudeBetween(
        double minLatitute, double maxLatitute,
        double minlongitude, double maxlongitude);

    Page<RestaurantEntity> findAll(Pageable pageable);

}
