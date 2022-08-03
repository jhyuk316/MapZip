package com.jhyuk316.mapzip.api;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantApiControllerTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private WebTestClient webTestClient;


    @Disabled
    @Test
    void getRestaurants() throws Exception {
        // given
        String restaurantName = UUID.randomUUID().toString();
        String address = UUID.randomUUID().toString();
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(restaurantName)
                .address(address)
                .latitude(128.1234)
                .longitude(36.1234)
                .build();
        restaurantRepository.save(restaurant);

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/restaurants").exchange();
        // WebTestClient.ResponseSpec exchange = webTestClient.get().uri("/api/v1/restaurants").accept(MediaType.APPLICATION_JSON)


        // then
        exchange.expectStatus().isOk();

    }

    @Disabled
    @Test
    void addRestaurants() {
        // given
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();

        // when

        // then


    }

    @Disabled
    @Test
    void testGetRestaurants() {
    }
}