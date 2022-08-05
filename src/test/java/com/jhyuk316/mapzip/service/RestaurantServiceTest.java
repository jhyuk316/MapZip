package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.model.*;
import com.jhyuk316.mapzip.persistence.*;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantCategoryRepository restaurantCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private YoutuberRepository youtuberRepository;

    @Autowired
    private RestaurantYoutuberRepository restaurantYoutuberRepository;

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
        assertThat(findRestaurant.getName()).isEqualTo(restaurantDTO.getName());
        assertThat(findRestaurant.getAddress()).isEqualTo("경기도 성남시 분당구 불정로 6 NAVER그린팩토리");
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

        assertThat(thrown).hasMessageContaining("올바르지 않는 식당 정보");
    }

    @Test
    @DisplayName("식당저장_중복된 식당")
    void restaurantSaveDuplicate_X() {
        // given
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당")
                .address("경기도 성남시 분당구 불정로 6")
                .build();


        // when
        Long savedId = restaurantService.save(restaurantDTO);
        Throwable thrown = catchThrowable(() -> restaurantService.save(restaurantDTO));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown).hasMessageContaining("등록된 식당");
    }

    @Test
    @Disabled
    @DisplayName("주소 좌표 변환 Naver API")
    void addressToCoordinates() {
        // given
        String addressString = "경기도 성남시 분당구 불정로 6";

        // when
        RestaurantService.Address newAddress = restaurantService.addressToCoordinates(addressString);

        // then
        assertThat(newAddress.getRoad()).isEqualTo("경기도 성남시 분당구 불정로 6 NAVER그린팩토리");
        assertThat(newAddress.getLongitude()).isEqualTo(127.1054065);
        assertThat(newAddress.getLatitude()).isEqualTo(37.3595669);
    }

    @Test
    @DisplayName("카테고리 출력 패치조인")
    void getCategories() {
        // given
        String restaurantName = UUID.randomUUID().toString();

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(restaurantName)
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        restaurantRepository.save(restaurant);
        Long savedId = restaurant.getId();

        String tag1 = UUID.randomUUID().toString();
        String tag2 = UUID.randomUUID().toString();
        String tag3 = UUID.randomUUID().toString();
        restaurantService.addCategory(savedId, tag1);
        restaurantService.addCategory(savedId, tag2);
        restaurantService.addCategory(savedId, tag3);

        // when
        List<CategoryDTO> categories = restaurantService.getCategories(savedId);

        // then
        // assertThat(result.getName()).isEqualTo(restaurant.getName());
        // assertThat(result.getAddress()).isEqualTo(restaurant.getAddress());
        CategoryEntity category1 = categoryRepository.findByName(tag1).get();
        CategoryEntity category2 = categoryRepository.findByName(tag1).get();
        CategoryEntity category3 = categoryRepository.findByName(tag1).get();
        assertThat(categories).contains(new CategoryDTO(category1), new CategoryDTO(category2), new CategoryDTO(category3));
    }

    @Test
    @Disabled
    @DisplayName("카테고리 출력 패치조인X")
    void getCategories_notFetchJoin() {
        // given
        String restaurantName = UUID.randomUUID().toString();

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(restaurantName)
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        restaurantRepository.save(restaurant);
        long savedId = restaurant.getId();

        restaurantService.addCategory(savedId, "한식");
        restaurantService.addCategory(savedId, "김밥");
        restaurantService.addCategory(savedId, "돈까스");

        // when
        RestaurantEntity result = restaurantRepository.getById(savedId);

        // then
        System.out.println("result.getCategories() = " + result.getRestaurantCategories());
        result.getRestaurantCategories().forEach(restaurantCategory -> System.out.println(restaurantCategory.getCategory().getName()));

        assertThat(result.getName()).isEqualTo(restaurant.getName());
        assertThat(result.getAddress()).isEqualTo(result.getAddress());
        List<String> strings = result.getRestaurantCategories().stream().map(rc -> rc.getCategory().getName()).toList();
        assertThat(strings).isEqualTo(List.of("한식", "김밥", "돈까스"));
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

    @Test
    @DisplayName("카테고리 추가")
    void addCategory() {
        // given
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        restaurantRepository.save(restaurant);

        // when
        restaurantService.addCategory(restaurant.getId(), "한식");

        // then
        CategoryEntity category = categoryRepository.findByName("한식").get();
        RestaurantCategoryEntity restaurantCategory = restaurantCategoryRepository.findByRestaurantAndCategory(restaurant, category).get();
        assertThat(restaurantCategory.getRestaurant().getName()).isEqualTo(restaurant.getName());
        assertThat(restaurantCategory.getCategory().getName()).isEqualTo(category.getName());
    }

    @Test
    @DisplayName("카테고리 중복 추가")
    void addCategory_X() {
        // given
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        restaurantRepository.save(restaurant);

        // when
        restaurantService.addCategory(restaurant.getId(), "한식");
        Throwable thrown = catchThrowable(() -> restaurantService.addCategory(restaurant.getId(), "한식"));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        assertThat(thrown.getMessage()).contains("카테고리 중복");
    }

    @Test
    @DisplayName("유튜버 추가")
    void addYoutuber() {
        // given
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        YoutuberEntity youtuber = YoutuberEntity.builder()
                .name("유튜버A")
                .channelId("Abcd")
                .build();


        restaurantRepository.save(restaurant);
        youtuberRepository.save(youtuber);
        String videoId = "Qwer";

        // when
        restaurantService.addYoutuber(restaurant.getId(), youtuber.getId(), videoId);

        // then
        RestaurantYoutuberEntity restaurantYoutuber = restaurantYoutuberRepository.findByRestaurantAndYoutuber(restaurant, youtuber).get();
        assertThat(restaurantYoutuber.getRestaurant().getId()).isEqualTo(restaurant.getId());
        assertThat(restaurantYoutuber.getYoutuber().getId()).isEqualTo(youtuber.getId());
        assertThat(restaurantYoutuber.getVideoId()).isEqualTo(videoId);
    }

    @Test
    @DisplayName("유튜버 리스트 가져오기")
    void getYoutubers() {
        // given
        YoutuberEntity youtuber1 = YoutuberEntity.builder()
                .name("유튜버A")
                .channelId("ChaA")
                .build();
        youtuberRepository.save(youtuber1);

        YoutuberEntity youtuber2 = YoutuberEntity.builder()
                .name("유튜버B")
                .channelId("ChaB")
                .build();
        youtuberRepository.save(youtuber2);

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name("식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();
        restaurantRepository.save(restaurant);

        restaurantService.addYoutuber(restaurant.getId(), youtuber1.getId(), "VideoA");
        restaurantService.addYoutuber(restaurant.getId(), youtuber2.getId(), "VideoB");

        // when
        List<RestaurantDTO.InnerYoutuberDTO> youtubers = restaurantService.getYoutubers(restaurant.getId());

        // then
        RestaurantDTO.InnerYoutuberDTO innerYoutuberDTO1 = new RestaurantDTO.InnerYoutuberDTO(youtuber1, "VideoA");
        RestaurantDTO.InnerYoutuberDTO innerYoutuberDTO2 = new RestaurantDTO.InnerYoutuberDTO(youtuber2, "VideoB");
        assertThat(youtubers).contains(innerYoutuberDTO1, innerYoutuberDTO2);
    }
}
