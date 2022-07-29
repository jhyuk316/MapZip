package com.jhyuk316.mapzip;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
// @Component
public class InitTestDB {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    @PostConstruct
    public void insertDATA() {
        insertRestaurant("시험식당1", "서울특별시 관악구 봉천동 962-1", "한식", "김밥", "돈까스");
        insertRestaurant("시험식당2", "서울특별시 관악구 신림동 1422-29", "한식", "김치찌개");
        insertRestaurant("시험식당3", "남부순환로 1667", "중식", "탕수육");
    }

    private void insertRestaurant(String name, String address, String... category) {
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(name)
                .address(address)
                .build();

        restaurantRepository.save(restaurant);
        Long savedId = restaurant.getId();

        for (String c : category) {
            restaurantService.addCategory(savedId, c);
        }
    }

}
