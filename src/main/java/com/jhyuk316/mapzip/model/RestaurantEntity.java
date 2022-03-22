package com.jhyuk316.mapzip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Restaurant")
public class RestaurantEntity {
    @Id
    private int id;
    private String restaurantname;
    private double latitude;
    private double longitude;
    private String address;
    private float rating;
}
