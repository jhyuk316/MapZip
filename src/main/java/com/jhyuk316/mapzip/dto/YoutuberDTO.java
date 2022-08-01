package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.YoutuberEntity;
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
public class YoutuberDTO {
    private long id;
    private String name;
    private String channelId;

    private final List<RestaurantDTO> restaurants = new ArrayList<>();

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

}
