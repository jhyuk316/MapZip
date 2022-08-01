package com.jhyuk316.mapzip.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.jhyuk316.mapzip.model.YoutuberEntity;

@Repository
public interface YoutuberRepository extends JpaRepository<YoutuberEntity, Integer> {
    Optional<YoutuberEntity> findById(Long id);

    List<YoutuberEntity> findByName(String name);

    List<YoutuberEntity> findByNameContains(String name);

    @Query("select y " +
            "from YoutuberEntity y " +
            "join fetch y.restaurantYoutubers ry " +
            "join fetch ry.restaurant r " +
            "where y.id = :id")
    Optional<YoutuberEntity> findByIdWithRestaurant(@Param("id") Long id);

}
