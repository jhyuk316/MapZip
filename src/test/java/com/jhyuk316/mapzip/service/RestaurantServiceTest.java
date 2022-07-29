package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @DisplayName("식당 저장")
    void restaurantSave_O() {
        // given
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당")
                .address("경기도 성남시 분당구 불정로 6")
                .build();

        // when
        Long savedId = restaurantService.save(restaurantDTO);

        // then
        RestaurantEntity findRestaurant = restaurantRepository.findById(savedId).get();
        assertThat(savedId).isEqualTo(1);
        assertThat(findRestaurant.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(findRestaurant.getAddress()).isEqualTo(restaurantDTO.getAddress());
        assertThat(findRestaurant.getLongitude()).isEqualTo(127.1054065);
        assertThat(findRestaurant.getLatitude()).isEqualTo(37.3595669);
    }

    @Test
    @DisplayName("식당저장_정보누락")
    void restaurantSaveWithoutAddress_X() {
        // given
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당")
                .build();

        // when
        Throwable thrown = catchThrowable(() -> restaurantService.save(restaurantDTO));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Disabled
    @DisplayName("주소 좌표 변환 Naver API")
    void addressToCoordinates() {
        // given
        String address = "경기도 성남시 분당구 불정로 6";

        // when
        double[] coordinate1 = restaurantService.addressToCoordinates(address);

        // then
        assertThat(coordinate1[0]).isEqualTo(127.1054065);
        assertThat(coordinate1[1]).isEqualTo(37.3595669);
    }

    @Test
    @DisplayName("카테고리 출력")
    void getCategories() {
        // given
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        Long savedId = restaurantService.save(restaurantDTO);

        restaurantService.addCategory(savedId, "한식");
        restaurantService.addCategory(savedId, "김밥");
        restaurantService.addCategory(savedId, "돈까스");

        // when
        RestaurantDTO result = restaurantService.getCategories(savedId);

        // then
        System.out.println("result.getCategories() = " + result.getCategories());
        assertThat(result.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(result.getAddress()).isEqualTo(result.getAddress());
        assertThat(result.getCategories()).isEqualTo(List.of("한식", "김밥", "돈까스"));
    }
}