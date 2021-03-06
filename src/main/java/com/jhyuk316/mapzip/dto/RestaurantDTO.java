package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantDTO {

    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private float rating;

    public RestaurantDTO(final RestaurantEntity entity) {
        id = entity.getId();
        name = entity.getName();
        latitude = entity.getLatitude();
        longitude = entity.getLongitude();
        address = entity.getAddress();
        rating = entity.getRating();
    }

    public RestaurantEntity toEntity() {
        return RestaurantEntity.builder()
            .id(id)
            .name(name)
            .latitude(latitude)
            .longitude(longitude)
            .address(address)
            .rating(rating)
            .build();
    }


}
