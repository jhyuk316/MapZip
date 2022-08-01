package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.persistence.RestaurantYoutuberRepository;
import com.jhyuk316.mapzip.persistence.YoutuberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YoutuberServiceTest {
    @Autowired
    private YoutuberService youtuberService;

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
    void getYoutuberWithRestaurant() {
    }
}