package com.jhyuk316.mapzip;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.service.RestaurantService;
import com.jhyuk316.mapzip.service.YoutuberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
// @Component
public class InitDB {
    private final RestaurantService restaurantService;
    private final YoutuberService youtuberService;

    @PostConstruct
    private void insertYoutuber() {
        insertYoutuber("유튜버A", "ChanA");
        insertYoutuber("유튜버B", "ChanB");
        insertYoutuber("유튜버C", "ChanC");
    }

    @PostConstruct
    private void insertRestaurat1() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당1")
                .address("서울특별시 관악구 봉천동 963-9")
                .build();

        Long savedId = restaurantService.save(restaurantDTO);

        restaurantService.addCategory(savedId, "한식");
        restaurantService.addCategory(savedId, "김밥");
        restaurantService.addCategory(savedId, "돈까스");

        restaurantService.addYoutuber(savedId, 1L, "VideoA1");
        restaurantService.addYoutuber(savedId, 2L, "VideoB1");
    }

    @PostConstruct
    private void insertRestaurat2() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당2")
                .address("서울특별시 관악구 신림동 1422-29")
                .build();

        Long savedId = restaurantService.save(restaurantDTO);

        restaurantService.addCategory(savedId, "한식");
        restaurantService.addCategory(savedId, "김치찌개");

        restaurantService.addYoutuber(savedId, 1L, "VideoA2");
    }


    @PostConstruct
    private void insertRestaurat3() {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("시험식당3")
                .address("남부순환로 1667")
                .build();

        Long savedId = restaurantService.save(restaurantDTO);

        restaurantService.addCategory(savedId, "중식");
        restaurantService.addCategory(savedId, "탕수육");
    }

    private void insertYoutuber(String name, String chanA) {
        YoutuberDTO youtuberDTO = YoutuberDTO.builder()
                .name(name)
                .channelId(chanA)
                .build();

        youtuberService.save(youtuberDTO);
    }

}
