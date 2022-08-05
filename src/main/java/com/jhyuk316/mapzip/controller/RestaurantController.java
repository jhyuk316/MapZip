package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String list(Model model) {
        List<RestaurantDTO> restaurantDTOS = restaurantService.getRestaurantByCoordination(37.4843, 126.9297, 3);
        model.addAttribute("restaurants", restaurantDTOS);
        return "restaurants/restaurantList";
    }

    @GetMapping("/restaurants/new")
    public String createRestaurants(Model model) {
        model.addAttribute("restaurantForm", new RestaurantDTO());
        return "restaurants/createRestaurantForm";
    }

    @PostMapping("/restaurants/new")
    public String create(@Valid @ModelAttribute("restaurantForm") RestaurantDTO restaurantForm, BindingResult result) {
        if (result.hasErrors()) {
            return "restaurants/createRestaurantForm";
        }

        restaurantService.save(restaurantForm);
        return "redirect:/";
    }

    @GetMapping("/restaurants/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(id);
        List<RestaurantDTO.InnerYoutuberDTO> youtubers = restaurantService.getYoutubers(id);
        List<CategoryDTO> categories = restaurantService.getCategories(id);
        restaurantDTO.setYoutubers(youtubers);
        restaurantDTO.setCategories(categories.stream().map(CategoryDTO::getName).toList());

        model.addAttribute("restaurantDTO", restaurantDTO);
        return "restaurants/restaurantDetail";
    }

}
