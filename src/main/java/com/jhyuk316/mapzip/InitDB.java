package com.jhyuk316.mapzip;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class InitDB {
    private final RestaurantService restaurantService;

    @PostConstruct
    private void insertRestaurat1() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        Long savedId = restaurantService.save(restaurantDTO);

        restaurantService.addCategory(savedId, "한식");
        restaurantService.addCategory(savedId, "김밥");
        restaurantService.addCategory(savedId, "돈까스");
    }

    @PostConstruct
    private void insertRestaurat2() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당2")
                .address("서울특별시 관악구 신림동 1422-29")
                .build();

        restaurantService.save(restaurantDTO);
    }


    @PostConstruct
    private void insertRestaurat3() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당3")
                .address("남부순환로 1667")
                .build();

        restaurantService.save(restaurantDTO);
    }


}
