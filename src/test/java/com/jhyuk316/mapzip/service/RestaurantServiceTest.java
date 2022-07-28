package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("식당 저장")
    void restaurantSave_O() {
        // gevin
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당")
                .address("경기도 성남시 분당구 불정로 6")
                .build();

        // when
        Long savedId = restaurantService.save(restaurantDTO);

        // then
        RestaurantEntity findRestaurant = restaurantRepository.findById(savedId).orElse(new RestaurantEntity());
        assertThat(savedId).isEqualTo(1);
        assertThat(restaurantDTO.getName()).isEqualTo(findRestaurant.getName());
        assertThat(restaurantDTO.getAddress()).isEqualTo(findRestaurant.getAddress());
    }

    @Test
    @Transactional
    @DisplayName("식당저장_정보누락")
    void restaurantSaveWithoutAddress_X() {
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
    @DisplayName("주소 좌표 변환 Naver API")
    void addressToCoordinates() {
        double[] coordinate1 = restaurantService.addressToCoordinates("경기도 성남시 분당구 불정로 6");

        assertThat(coordinate1[0]).isEqualTo(127.1054065);
        assertThat(coordinate1[1]).isEqualTo(37.3595669);
    }
}