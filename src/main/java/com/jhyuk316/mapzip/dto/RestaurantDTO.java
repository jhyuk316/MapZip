package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantDTO {
    private long id;
    @NotEmpty(message = "식당 이름은 필수에요.")
    private String name;
    private double latitude;
    private double longitude;
    @NotEmpty(message = "식당 주소는 필수에요.")
    private String address;
    private float rating;

    private final List<String> categories = new ArrayList<>();

    private final List<InnerYoutuberDTO> youtubers = new ArrayList<>();

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

    @Data
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
