package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.persistence.RestaurantYoutuberRepository;
import com.jhyuk316.mapzip.persistence.YoutuberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class YoutuberServiceTest {
    @Autowired
    private YoutuberService youtuberService;
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private YoutuberRepository youtuberRepository;

    @Autowired
    private RestaurantYoutuberRepository restaurantYoutuberRepository;

    @Test
    void save() {
        // given
        YoutuberDTO youtuberDTO = YoutuberDTO.builder()
                .name("유튜버A")
                .channelId("Abcd")
                .build();

        // when
        Long savedId = youtuberService.save(youtuberDTO);

        // then
        YoutuberEntity youtuber = youtuberRepository.findById(savedId).get();
        assertThat(youtuberDTO.getName()).isEqualTo(youtuber.getName());
        assertThat(youtuberDTO.getChannelId()).isEqualTo(youtuber.getChannelId());
    }

    @Test
    @DisplayName("유튜버 저장 채널명, 채널ID 누락")
    void saveWithoutNameAndId_X() {
        // given
        YoutuberDTO youtuberDTO = YoutuberDTO.builder()
                .build();

        // when
        Throwable thrown = catchThrowable(() -> youtuberService.save(youtuberDTO));

        // then
        assertThat(thrown).hasMessageContaining("채널이름");
        assertThat(thrown).hasMessageContaining("채널ID");
    }

    @Test
    @DisplayName("유튜버 저장 중복")
    void saveDuplicate_X() {
        // given
        YoutuberDTO youtuberDTO1 = YoutuberDTO.builder()
                .name("유튜버A")
                .channelId("Abcd")
                .build();

        YoutuberDTO youtuberDTO2 = YoutuberDTO.builder()
                .name("유튜버B")
                .channelId("Abcd")
                .build();

        youtuberService.save(youtuberDTO1);

        // when
        Throwable thrown = catchThrowable(() -> youtuberService.save(youtuberDTO2));

        // then
        assertThat(thrown).hasMessageContaining("이미 있는 유튜버");
    }


    @Test
    void getYoutuberWithRestaurant() {
        // given
        YoutuberEntity youtuber = YoutuberEntity.builder()
                .name("유튜버A")
                .channelId("Abcd")
                .build();
        youtuberRepository.save(youtuber);

        RestaurantEntity restaurant1 = RestaurantEntity.builder()
                .name("시험식당1")
                .address("서울특별시 관악구 봉천동 962-1")
                .build();
        RestaurantEntity restaurant2 = RestaurantEntity.builder()
                .name("시험식당2")
                .address("서울특별시 관악구 신림동 1422-29")
                .build();
        RestaurantEntity restaurant3 = RestaurantEntity.builder()
                .name("시험식당3")
                .address("남부순환로 1667")
                .build();
        restaurantRepository.save(restaurant1);
        restaurantRepository.save(restaurant2);
        restaurantRepository.save(restaurant3);


        String videoId1 = "Qwer";
        String videoId2 = "Asdf";
        String videoId3 = "Zxcv";

        restaurantService.addYoutuber(restaurant1.getId(), youtuber.getId(), videoId1);
        restaurantService.addYoutuber(restaurant2.getId(), youtuber.getId(), videoId2);
        restaurantService.addYoutuber(restaurant3.getId(), youtuber.getId(), videoId3);

        // when
        YoutuberDTO youtuberDTO = youtuberService.getYoutuberWithRestaurant(youtuber.getId());

        // then
        assertThat(youtuberDTO.getName()).isEqualTo(youtuber.getName());
        assertThat(youtuberDTO.getChannelId()).isEqualTo(youtuber.getChannelId());
        assertThat(youtuberDTO.getRestaurants().size()).isEqualTo(3);

        YoutuberDTO.InnerRestaurantDTO innerRestaurantDTO1 = new YoutuberDTO.InnerRestaurantDTO(restaurant1);
        innerRestaurantDTO1.setVideoId(videoId1);
        YoutuberDTO.InnerRestaurantDTO innerRestaurantDTO2 = new YoutuberDTO.InnerRestaurantDTO(restaurant2);
        innerRestaurantDTO2.setVideoId(videoId2);
        YoutuberDTO.InnerRestaurantDTO innerRestaurantDTO3 = new YoutuberDTO.InnerRestaurantDTO(restaurant3);
        innerRestaurantDTO3.setVideoId(videoId3);
        assertThat(youtuberDTO.getRestaurants()).contains(innerRestaurantDTO1, innerRestaurantDTO2, innerRestaurantDTO3);
    }
}