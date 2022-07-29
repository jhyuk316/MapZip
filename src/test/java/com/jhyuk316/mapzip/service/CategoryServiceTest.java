package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @BeforeEach
    public void insertDATA() {
        insertRestaurant("시험식당1", "서울특별시 관악구 봉천동 962-1", "한식", "김밥", "돈까스");
        insertRestaurant("시험식당2", "서울특별시 관악구 신림동 1422-29", "한식", "김치찌개");
        insertRestaurant("시험식당3", "남부순환로 1667", "중식", "탕수육");
    }

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

        // when
        Long getId = categoryService.getCategory("한식").getId();
        CategoryDTO result = categoryService.getRestaurants(getId);

        // then
        System.out.println("result.getRestaurants() = " + result.getRestaurants());
        assertThat(result.getName()).isEqualTo("한식");
        assertThat(result.getRestaurants()).isEqualTo(List.of("시험식당1", "시험식당2"));
        // TODO 왜 두번 중복 되서 나오는가? 왜?!
    }

    @Test
    @DisplayName("카테고리 이름으로 식당리스트 조회")
    void getRestaurantsByName() {
        // given

        // when
        CategoryDTO result = categoryService.getRestaurants("한식");
        System.out.println("size = " + restaurantRepository.findAll().size());

        // then
        System.out.println("result.getRestaurants() = " + result.getRestaurants());
        assertThat(result.getName()).isEqualTo("한식");
        assertThat(result.getRestaurants()).isEqualTo(List.of("시험식당1", "시험식당2"));
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