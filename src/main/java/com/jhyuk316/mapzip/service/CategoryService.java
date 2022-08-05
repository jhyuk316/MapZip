package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.model.CategoryEntity;
import com.jhyuk316.mapzip.model.RestaurantCategoryEntity;
import com.jhyuk316.mapzip.persistence.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::new).toList();
    }

    public CategoryDTO getCategory(String name) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("존재 하지 않는 카테고리에요.");
        }
        CategoryEntity category = optionalCategory.get();

        return new CategoryDTO(category);
    }

    public CategoryDTO getRestaurants(Long categoryId) {
        CategoryEntity category = categoryRepository.findByIdWithRestaurants(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 카테고리_ID"));

        CategoryDTO categoryDTO = new CategoryDTO(category);
        for (RestaurantCategoryEntity rc : category.getRestaurantCategories()) {
            categoryDTO.getRestaurants().add(rc.getRestaurant().getName());
        }

        return categoryDTO;
    }

    public CategoryDTO getRestaurants(String categoryName) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByNameWithRestaurants(categoryName);
        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("없는 카테고리에요.");
        }
        CategoryEntity category = optionalCategory.get();

        CategoryDTO categoryDTO = new CategoryDTO(category);
        for (RestaurantCategoryEntity rc : category.getRestaurantCategories()) {
            categoryDTO.getRestaurants().add(rc.getRestaurant().getName());
        }

        return categoryDTO;
    }

    public Long save(String categoryName) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByName(categoryName);

        CategoryEntity category = new CategoryEntity(categoryName);
        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            categoryRepository.save(category);
        }
        return category.getId();
    }

    public Long save(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findByName(categoryDTO.getName());

        if (optionalCategory.isPresent()) {
            return optionalCategory.get().getId();
        } else {
            CategoryEntity category = new CategoryEntity(categoryDTO);
            categoryRepository.save(category);
            return category.getId();
        }
    }
}
