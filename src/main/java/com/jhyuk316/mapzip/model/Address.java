package com.jhyuk316.mapzip.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private String street;
    private double latitude;
    private double longitude;

    public void setAddress(String street){
        this.street = street;
        // 좌표 변환
        // latitude, longitude 채우기
    }
    
    public void setAddress(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        
        // 주소 변환
        // street 채우기
    }
}
