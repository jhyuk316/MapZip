package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @Transactional
    void 식당저장_주소() {
        // gevin
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당")
                .address("경기도 성남시 분당구 불정로 6 그린팩토리")
                .build();

        // when
        Long savedId = restaurantService.save(restaurantDTO);

        // then
        RestaurantEntity findRestaurant = restaurantRepository.findById(savedId).get();
        assertThat(savedId).isEqualTo(1);
        assertThat(restaurantDTO.getName()).isEqualTo(findRestaurant.getName());
        assertThat(restaurantDTO.getAddress()).isEqualTo(findRestaurant.getAddress());
    }

    @Test
    @Transactional
    void 식당저장_정보누락() {
        // gevin
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> restaurantService.save(restaurantDTO));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addressToCoordinates() {
        restaurantService.addressToCoordinates("경기도 성남시 분당구 불정로 6");
        restaurantService.addressToCoordinates("경기도 성남시 분당구 정자동 178-1");
    }
}