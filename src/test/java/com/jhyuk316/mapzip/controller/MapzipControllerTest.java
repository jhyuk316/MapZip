package com.jhyuk316.mapzip.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


// 스프링의 모든 빈을 로드하여 테스트 실행
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// 해당 컨트롤러 클래스만 로드하여 테스트 실행
//@WebMvcTest(controllers = MapzipController.class)
public class MapzipControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @AfterEach
    public void tearDown() throws Exception {
        restaurantRepository.deleteAll();
    }

    @Test
    void testHello() throws Exception {
        String hello = "hello";

//        mvc.perform(get("/mapzip/hello")).andExpect(status().isOk())
//            .andExpect(content().string(hello));
    }


    @Test
    void getRestaurantList() throws Exception {

    }

    @Test
    @DisplayName("음식점 추가")
    void addRestarurant() throws Exception {
        //given
        String name = "테스트 식당";
        String address = "서울특별시 관악구 청룡동 남부순환로 1820";

        RestaurantDTO mapzipDTO = RestaurantDTO.builder()
            .name(name)
            .address(address)
            .build();

        String url = "http://localhost:" + port + "/mapzip/addRestaurant";

        //when
        ResponseEntity<RestaurantDTO> responseEntity = restTemplate.postForEntity(url, mapzipDTO,
                RestaurantDTO.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEqualTo(null);

        List<RestaurantEntity> all = restaurantRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("근처 식당 목록")
    void nearbyRestaurants() {

    }
}
