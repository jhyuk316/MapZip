package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.YoutuberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoutuberDTO {
    private long id;
    private String name;
    private String url;

    public YoutuberDTO(final YoutuberEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.url = entity.getUrl();
    }

    public YoutuberEntity toEntity() {
        return YoutuberEntity.builder()
            .id(id)
            .name(name)
            .url(url)
            .build();
    }

}
