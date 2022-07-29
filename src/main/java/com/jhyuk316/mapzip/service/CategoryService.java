package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.model.CategoryEntity;
import com.jhyuk316.mapzip.model.RestaurantCategoryEntity;
import com.jhyuk316.mapzip.persistence.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDTO getCategory(String name) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("존재 하지 않는 카테고리에요.");
        }
        CategoryEntity category = optionalCategory.get();

        return new CategoryDTO(category);
    }

    public CategoryDTO getRestaurants(Long id) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByIdWithRestaurants(id);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("잘못된 카테고리");
        }

        CategoryEntity category = optionalCategory.get();

        System.out.println("category.getRestaurantCategories().size() = " + category.getRestaurantCategories().size());

        CategoryDTO categoryDTO = new CategoryDTO(category);
        for (RestaurantCategoryEntity rc : category.getRestaurantCategories()) {
            System.out.println("rc.getId() = " + rc.getId());
            System.out.println("rc.getCategory() = " + rc.getCategory().getName());
            System.out.println("rc.getRestaurant() = " + rc.getRestaurant().getName());
            categoryDTO.getRestaurants().add(rc.getRestaurant().getName());
        }

        return categoryDTO;
    }
}
