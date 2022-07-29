package com.jhyuk316.mapzip.model;

import javax.persistence.*;

import lombok.*;

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
    private String url;

    @Builder
    public YoutuberEntity(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
