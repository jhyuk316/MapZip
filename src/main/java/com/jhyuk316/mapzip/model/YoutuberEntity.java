package com.jhyuk316.mapzip.model;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "youtuber")
public class YoutuberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "youtuber_id")
    private long id;

    private String name;
    private String channelId;

    @OneToMany(mappedBy = "youtuber")
    private final List<RestaurantYoutuberEntity> restaurantYoutubers = new ArrayList<>();

    @Builder
    public YoutuberEntity(long id, String name, String channelId) {
        this.id = id;
        this.name = name;
        this.channelId = channelId;
    }

    public void addRestaurantYoutuber(RestaurantYoutuberEntity restaurantYoutuber) {
        this.restaurantYoutubers.add(restaurantYoutuber);
    }


}
