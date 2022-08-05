package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<CategoryDTO> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "categories/categoryList";
    }

    @GetMapping("/categories/new")
    public String create(Model model) {
        model.addAttribute("categoryForm", new CategoryDTO());
        return "categories/createCategoryForm";
    }

    @PostMapping("/categories/new")
    public String create(@Valid @ModelAttribute("categoryForm") CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/createCategoryForm";
        }

        categoryService.save(categoryDTO);
        return "redirect:/";
    }
}
