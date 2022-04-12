package com.jhyuk316.mapzip.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @AfterEach
    public void cleanup(){
        restaurantRepository.deleteAll();
    }

    @Test
    void testFindByRestaurantname() {
        //given
        String name = "테스트식당";
        double latitude = 37.481252;
        double longitude = 126.952757;

        restaurantRepository.save(RestaurantEntity.builder()
                .restaurantname(name)
                .latitude(latitude)
                .longitude(longitude)
                .build());

        //when
        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findAll();

        //then
        RestaurantEntity restaurantEntity = restaurantEntityList.get(0);
        assertThat(restaurantEntity.getRestaurantname()).isEqualTo(name);
        assertThat(restaurantEntity.getLatitude()).isEqualTo(latitude);
        assertThat(restaurantEntity.getLongitude()).isEqualTo(longitude);
    }

    @Test
    void testFindByRestaurantnameContains() {

    }
}
