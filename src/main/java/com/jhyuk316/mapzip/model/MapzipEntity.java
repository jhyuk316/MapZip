package com.jhyuk316.mapzip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Mapzip")
public class MapzipEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    private String id;
    private String restName;
    private double latitude;
    private double longitude;
    private String address;
    private float rete;
}
