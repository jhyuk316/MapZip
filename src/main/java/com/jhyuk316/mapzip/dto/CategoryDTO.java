package com.jhyuk316.mapzip.dto;

import com.jhyuk316.mapzip.model.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private final List<String> restaurants = new ArrayList<>();

    public CategoryDTO(CategoryEntity category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
