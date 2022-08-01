package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoutuberDTO {
    private long id;
    @NotNull(message = "유튜브 채널이름은 필수에요")
    private String name;
    @NotNull(message = "유튜브 채널ID는 필수에요.")
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
