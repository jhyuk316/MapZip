package com.jhyuk316.mapzip.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MapzipServiceTest {

    @Autowired
    MapzipService mapzipService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    void save() {
        //given
        String name = "테스트 식당";
        String address = "서울특별시 관악구 청룡동 남부순환로 1820";

        MapzipDTO mapzipDTO = MapzipDTO.builder().restaurantname(name).address(address).build();

        List<RestaurantEntity> entities = mapzipService.save(mapzipDTO);
//        List<RestaurantEntity> entities = restaurantRepository.findAll();

        assertThat(entities.get(0).getRestaurantname()).isEqualTo(name);
        assertThat(entities.get(0).getAddress()).isEqualTo(address);
    }

    @Test
    void getAllRestaurant() {
    }
}