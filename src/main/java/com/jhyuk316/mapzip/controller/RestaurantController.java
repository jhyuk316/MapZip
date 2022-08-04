package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
