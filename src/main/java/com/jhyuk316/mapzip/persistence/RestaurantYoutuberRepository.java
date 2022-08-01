package com.jhyuk316.mapzip.persistence;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.RestaurantYoutuberEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantYoutuberRepository extends JpaRepository<RestaurantYoutuberEntity, Long> {

    Optional<RestaurantYoutuberEntity> findByRestaurantAndYoutuber(RestaurantEntity restaurant, YoutuberEntity youtuber);

}
