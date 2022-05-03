package com.jhyuk316.mapzip.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


class RestaurantDTOTest {

    @Test
    public void Dto테스트() {
        //given
        String restaurantname = "테스트식당";
        double latitude = 37.481252;
        double longitude = 126.952757;

        //when
        RestaurantDTO dto = RestaurantDTO.builder()
            .name(restaurantname)
            .latitude(latitude)
            .longitude(longitude)
            .build();

        assertThat(dto.getName()).isEqualTo(restaurantname);
        assertThat(dto.getLatitude()).isEqualTo(latitude);
        assertThat(dto.getLongitude()).isEqualTo(longitude);
    }

    @Test
    void toEntity() {
    }
}