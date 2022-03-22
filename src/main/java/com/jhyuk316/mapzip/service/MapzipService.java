package com.jhyuk316.mapzip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.jhyuk316.mapzip.model.*;
import com.jhyuk316.mapzip.persistence.*;

@Slf4j
@Service
public class MapzipService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private YoutuberRepository youtuberRepository;

    public String testService() {
        RestaurantEntity entity =
                RestaurantEntity.builder().restaurantname("first rest name").build();

        restaurantRepository.save(entity);

        RestaurantEntity savedEntity = restaurantRepository.findById(entity.getId()).get();

        return savedEntity.getRestaurantname();
    }

    public List<RestaurantEntity> getListRestaurant(final String restaurantName) {
        return restaurantRepository.findByRestaurantnameContains(restaurantName);
    }

    public List<RestaurantEntity> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    public List<YoutuberEntity> getAllYoutuber() {
        return youtuberRepository.findAll();
    }
}
