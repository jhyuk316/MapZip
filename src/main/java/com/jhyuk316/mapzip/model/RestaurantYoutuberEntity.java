package com.jhyuk316.mapzip.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RestaurantYoutuberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoId;

    @ManyToOne(fetch = FetchType.LAZY)
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    private YoutuberEntity youtuber;

    public RestaurantYoutuberEntity(RestaurantEntity restaurant, YoutuberEntity youtuber, String videoId) {
        this.videoId = videoId;
        this.restaurant = restaurant;
        this.youtuber = youtuber;
    }
}
