package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoutuberDTO {
    private long id;
    private String name;
    private String channelId;

    private final List<InnerRestaurantDTO> restaurants = new ArrayList<>();

    public YoutuberDTO(final YoutuberEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.channelId = entity.getChannelId();
    }

    public YoutuberEntity toEntity() {
        return YoutuberEntity.builder()
                .id(id)
                .name(name)
                .channelId(channelId)
                .build();
    }

    @EqualsAndHashCode
    static public class InnerRestaurantDTO {
        private final long id;
        private final String name;
        private final double latitude;
        private final double longtitude;
        private final String address;
        private final float rating;

        public InnerRestaurantDTO(RestaurantEntity restaurant) {
            this.id = restaurant.getId();
            this.name = restaurant.getName();
            this.latitude = restaurant.getLatitude();
            this.longtitude = restaurant.getLatitude();
            this.address = restaurant.getAddress();
            this.rating = restaurant.getRating();
        }
    }

}
