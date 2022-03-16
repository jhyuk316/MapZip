package com.jhyuk316.mapzip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.jhyuk316.mapzip.model.MapzipEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MapzipDTO {
    private String id;
    private String restName;
    private double latitude;
    private double longitude;
    private String address;
    private float rete;

    public MapzipDTO(final MapzipEntity entity) {
        this.id = entity.getId();
        this.restName = entity.getRestName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
        this.rete = entity.getRete();
    }

    public static MapzipEntity toEntity(final MapzipDTO dto) {
        return MapzipEntity.builder().id(dto.getId()).restName(dto.getRestName())
                .latitude(dto.getLatitude()).longitude(dto.getLongitude()).address(dto.getAddress())
                .rete(dto.getRete()).build();
    }

}
