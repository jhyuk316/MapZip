package com.jhyuk316.mapzip.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "restaurant_category")
public class RestaurantCategoryEntity {
    @Id
    private long id;

    // @ManyToOne
    // @JoinColumn(name = "id")
    // private RestaurantEntity restaurant;

    // @ManyToOne
    // @JoinColumn(name = "id")
    // private CategoryEntity category;

}
