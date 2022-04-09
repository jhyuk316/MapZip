package com.jhyuk316.mapzip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.jhyuk316.mapzip.model.RestaurantEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MapzipDTO {
    private long id;
    private String restaurantname;
    private double latitude;
    private double longitude;
    private String address;
    private float rating;

    public MapzipDTO(final RestaurantEntity entity) {
        this.id = entity.getId();
        this.restaurantname = entity.getRestaurantname();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
        this.rating = entity.getRating();
    }

    public static RestaurantEntity toEntity(final MapzipDTO dto) {
        return RestaurantEntity.builder().id(dto.getId()).restaurantname(dto.getRestaurantname())
                .latitude(dto.getLatitude()).longitude(dto.getLongitude()).address(dto.getAddress())
                .rating(dto.getRating()).build();
    }

    public RestaurantEntity toEntity() {
        return RestaurantEntity.builder().id(id).restaurantname(restaurantname)
                .latitude(latitude).longitude(longitude).address(address)
                .rating(rating).build();
    }

}
