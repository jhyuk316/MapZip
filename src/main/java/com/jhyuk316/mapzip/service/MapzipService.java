package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.persistence.YoutuberRepository;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MapzipService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private YoutuberRepository youtuberRepository;


    public List<RestaurantEntity> save(RestaurantDTO restaurantDTO) {
        if (restaurantDTO == null) {
            log.warn("Dto cannot be null");
            throw new RuntimeException("Dto cannot be null");
        }

        RestaurantEntity restaurantEntity = restaurantDTO.toEntity();
        if (restaurantEntity.getName() == null || restaurantEntity.getAddress() == null) {
            log.warn("Invaild Restaturant data");
            throw new RuntimeException("Invaild Restaturant data");
        }
        restaurantEntity.setId(0);

        log.info("Entity id : {}, name : {} is tried to save", restaurantEntity.getId(),
            restaurantEntity.getName());

        restaurantRepository.save(restaurantEntity);

        log.info("Entity id : {}, name : {} is saved", restaurantEntity.getId(),
            restaurantEntity.getName());

        return restaurantRepository.findById(restaurantEntity.getId());
    }

    public List<RestaurantEntity> findRestaurants(final String name) {
        return restaurantRepository.findByNameContains(name);
    }

    public Page<RestaurantEntity> getRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public RestaurantDTO getRestaurant(long id) {
        RestaurantEntity entity = restaurantRepository.getById(id);
        return new RestaurantDTO(entity);
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
