package com.jhyuk316.mapzip.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.jhyuk316.mapzip.model.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);

    @Query("select c " +
            "from CategoryEntity c " +
            "join fetch c.restaurantCategories rc " +
            "join fetch rc.restaurant r " +
            "where c.id = :id")
    Optional<CategoryEntity> findByIdWithRestaurants(@Param("id") Long id);

}
