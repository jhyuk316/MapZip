package com.jhyuk316.mapzip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.jhyuk316.mapzip.model.YoutuberEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoutuberDTO {
    private int id;
    private String name;
    private String url;

    public YoutuberDTO(final YoutuberEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.url = entity.getUrl();
    }

    public static YoutuberEntity toEntity(final YoutuberDTO dto) {
        return YoutuberEntity.builder().id(dto.getId()).name(dto.getName()).url(dto.getUrl())
                .build();
    }

}
