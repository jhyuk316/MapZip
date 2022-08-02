package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import org.hibernate.id.UUIDGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CategoryService categoryService;

    @Test
    void getCategory() {
        // given

        // when
        String categoryName = "한식";
        CategoryDTO categoryDTO = categoryService.getCategory(categoryName);

        // then
        // assertThat(categoryDTO.getId()).isEqualTo(1L);
        assertThat(categoryDTO.getName()).isEqualTo(categoryName);
    }

    @Test
    @DisplayName("카테고리 ID로 식당리스트 조회")
    void getRestaurants() {
        // given
        String restaurantName1 = UUID.randomUUID().toString();
        String restaurantName2 = UUID.randomUUID().toString();
        String restaurantName3 = UUID.randomUUID().toString();
        String tag1 = UUID.randomUUID().toString();
        Long savedId1 = insertRestaurant(restaurantName1, "서울특별시 관악구 봉천동 962-1", tag1, "김밥", "돈까스");
        Long savedId2 = insertRestaurant(restaurantName2, "서울특별시 관악구 신림동 1422-29", tag1, "김치찌개");
        Long savedId3 = insertRestaurant(restaurantName3, "남부순환로 1667", "중식", "탕수육");

        // when
        Long getId = categoryService.getCategory(tag1).getId();
        CategoryDTO result = categoryService.getRestaurants(getId);

        // then
        assertThat(result.getName()).isEqualTo(tag1);
        assertThat(result.getRestaurants()).contains(restaurantName1, restaurantName2);

        // TODO 왜 두번 중복 되서 나오는가? 왜?! 랜덤이 답인가?
    }

    @Test
    @DisplayName("카테고리 이름으로 식당리스트 조회")
    void getRestaurantsByName() {
        // given
        String restaurantName1 = UUID.randomUUID().toString();
        String restaurantName2 = UUID.randomUUID().toString();
        String restaurantName3 = UUID.randomUUID().toString();
        String tag1 = UUID.randomUUID().toString();
        Long savedId1 = insertRestaurant(restaurantName1, "서울특별시 관악구 봉천동 962-1", tag1, "김밥", "돈까스");
        Long savedId2 = insertRestaurant(restaurantName2, "서울특별시 관악구 신림동 1422-29", tag1, "김치찌개");
        Long savedId3 = insertRestaurant(restaurantName3, "남부순환로 1667", "중식", "탕수육");

        // when
        CategoryDTO result = categoryService.getRestaurants(tag1);

        // then
        assertThat(result.getName()).isEqualTo(tag1);
        assertThat(result.getRestaurants()).contains(restaurantName1, restaurantName2);
    }

    private Long insertRestaurant(String name, String address, String... category) {
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(name)
                .address(address)
                .build();

        restaurantRepository.save(restaurant);
        Long savedId = restaurant.getId();

        for (String c : category) {
            restaurantService.addCategory(savedId, c);
        }

        return savedId;
    }


}