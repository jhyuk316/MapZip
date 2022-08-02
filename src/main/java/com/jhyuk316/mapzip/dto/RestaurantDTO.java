package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import lombok.*;

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

    private List<String> categories;

    private List<InnerYoutuberDTO> youtubers;

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

    @EqualsAndHashCode
    public static class InnerYoutuberDTO {
        private final long id;
        private final String name;
        private final String channelId;

        public InnerYoutuberDTO(YoutuberEntity youtuber) {
            this.id = youtuber.getId();
            this.name = youtuber.getName();
            this.channelId = youtuber.getChannelId();
        }
    }
}
