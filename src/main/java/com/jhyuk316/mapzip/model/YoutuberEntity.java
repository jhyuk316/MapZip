package com.jhyuk316.mapzip.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "youtuber")
public class YoutuberEntity {
    @Id
    private int id;
    private String name;
    private String url;
}
