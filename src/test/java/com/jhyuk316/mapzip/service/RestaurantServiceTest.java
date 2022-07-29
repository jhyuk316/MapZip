package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    // @BeforeEach
    public void insertDATA() {
        insertRestaurant("시험식당1", "서울특별시 관악구 봉천동 962-1", "한식", "김밥", "돈까스");
        insertRestaurant("시험식당2", "서울특별시 관악구 신림동 1422-29", "한식", "김치찌개");
        insertRestaurant("시험식당3", "남부순환로 1667", "중식", "탕수육");
    }

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
    @DisplayName("카테고리 출력 패치조인")
    void getCategories() {
        // given
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("시험식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        restaurantRepository.save(restaurant);
        Long savedId = restaurant.getId();

        restaurantService.addCategory(savedId, "한식");
        restaurantService.addCategory(savedId, "김밥");
        restaurantService.addCategory(savedId, "돈까스");

        // when

        RestaurantDTO result = restaurantService.getCategories(savedId);

        // then
        System.out.println("result.getCategories() = " + result.getCategories());
        assertThat(result.getName()).isEqualTo(restaurant.getName());
        assertThat(result.getAddress()).isEqualTo(restaurant.getAddress());
        assertThat(result.getCategories()).isEqualTo(List.of("한식", "김밥", "돈까스"));
    }

    @Test
    @Disabled
    @DisplayName("카테고리 출력 패치조인X")
    void getCategories_notFetchJoin() {
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
        RestaurantEntity result = restaurantRepository.getById(savedId);

        // then
        System.out.println("result.getCategories() = " + result.getRestaurantCategories());
        result.getRestaurantCategories().forEach(restaurantCategory -> System.out.println(restaurantCategory.getCategory().getName()));

        assertThat(result.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(result.getAddress()).isEqualTo(result.getAddress());
        // assertThat(result.getCategories()).isEqualTo(List.of("한식", "김밥", "돈까스"));
    }

    private void insertRestaurant(String name, String address, String... category) {
        RestaurantEntity restaurant1 = RestaurantEntity.builder()
                .name(name)
                .address(address)
                .build();

        restaurantRepository.save(restaurant1);
        Long savedId = restaurant1.getId();

        for (String c : category) {
            restaurantService.addCategory(savedId, c);
        }
    }
}