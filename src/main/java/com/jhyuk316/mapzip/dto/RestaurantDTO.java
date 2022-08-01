package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantDTO {
    private long id;
    private String name;
    private double latitude;
    private double longtitude;
    private String address;
    private float rating;

    private final List<String> categories = new ArrayList<>();

    private final List<YoutuberDTO> youtubers = new ArrayList<>();

    public RestaurantDTO(final RestaurantEntity entity) {
        id = entity.getId();
        name = entity.getName();
        latitude = entity.getLatitude();
        longtitude = entity.getLongitude();
        address = entity.getAddress();
        rating = entity.getRating();
    }

    public RestaurantEntity toEntity() {
        return RestaurantEntity.builder()
                .id(id)
                .name(name)
                .latitude(latitude)
                .longitude(longtitude)
                .address(address)
                .rating(rating)
                .build();
    }


}
