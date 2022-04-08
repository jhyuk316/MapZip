package com.jhyuk316.mapzip.service;

import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import com.jhyuk316.mapzip.model.*;
import com.jhyuk316.mapzip.persistence.*;
import org.springframework.web.bind.annotation.RequestParam;

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

        RestaurantEntity savedEntity = restaurantRepository.findById(entity.getId()).get(0);

        return savedEntity.getRestaurantname();
    }

    public List<RestaurantEntity> getListRestaurant(final String restaurantName) {
        return restaurantRepository.findByRestaurantnameContains(restaurantName);
    }

    public List<RestaurantEntity> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    public Page<RestaurantEntity> getAllRestaurant(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public List<YoutuberEntity> getAllYoutuber() {
        return youtuberRepository.findAll();
    }

    public List<RestaurantEntity> nearbyRestaurants(double latitude,
        double longitude) {
        double diff = 0.01;

        List<RestaurantEntity> restList = restaurantRepository.findByLatitudeBetweenAndLongitudeBetween(
            latitude - diff,
            latitude + diff, longitude - diff, longitude + diff);

        restList.sort(Comparator.comparingDouble(
            o -> (Math.abs(o.getLatitude() - latitude) + Math.abs(o.getLongitude() - longitude))));

//        restList.sort((o1, o2) ->
//            (int)((Math.abs(o1.getLatitude() - latitude) + Math.abs(o1.getLongitude() - longitude)) - (
//                Math.abs(o2.getLatitude() - latitude) + Math.abs(o2.getLongitude() - longitude))));

        return restList;
    }
}
