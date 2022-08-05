package com.jhyuk316.mapzip.api;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.dto.ResponseDTO;
import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import com.jhyuk316.mapzip.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RestaurantApiController {
    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants")
    public ResponseEntity<ResponseDTO<RestaurantDTO>> getRestaurants() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        List<RestaurantDTO> restaurantDTOS = restaurants.stream().map(RestaurantDTO::new).toList();
        ResponseDTO<RestaurantDTO> responseDTO = ResponseDTO.<RestaurantDTO>builder()
                .result(restaurantDTOS)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/restaurants", params = {"latitude", "longitude", "level"})
    public ResponseEntity<ResponseDTO<RestaurantDTO>> getRestaurantsByCoordination(
            @RequestParam(required = true) double latitude,
            @RequestParam(required = true) double longitude,
            @RequestParam(required = false, defaultValue = "3") int level) {

        List<RestaurantDTO> restaurantDTOS = restaurantService.getRestaurantByCoordination(latitude, longitude, level);

        ResponseDTO<RestaurantDTO> responseDTO = ResponseDTO.<RestaurantDTO>builder()
                .result(restaurantDTOS)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<ResponseDTO<Long>> addRestaurants(@RequestBody @Valid RestaurantDTO restaurantDTO) {
        Long savedId = restaurantService.save(restaurantDTO);
        ResponseDTO<Long> responseDTO = ResponseDTO.<Long>builder()
                .result(List.of(savedId))
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/restaurants/{id}")
    public ResponseEntity<ResponseDTO<RestaurantDTO>> getRestaurants(@PathVariable("id") @Min(0) Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 식당_ID에요"));

        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant);
        List<RestaurantDTO.InnerYoutuberDTO> youtubers = restaurantService.getYoutubers(id);
        List<CategoryDTO> categories = restaurantService.getCategories(id);
        restaurantDTO.setYoutubers(youtubers);
        restaurantDTO.setCategories(categories.stream().map(CategoryDTO::getName).toList());

        ResponseDTO<RestaurantDTO> responseDTO = ResponseDTO.<RestaurantDTO>builder()
                .result(List.of(restaurantDTO))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("restaurants/{id}/categories")
    public ResponseEntity<ResponseDTO<CategoryDTO>> getCategories(@PathVariable("id") Long id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 식당_ID에요"));

        List<CategoryDTO> categories = restaurantService.getCategories(id);

        ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder()
                .result(categories)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("restaurants/{id}/categories")
    public ResponseEntity<Long> setCategory(@PathVariable("id") Long id, String category) {
        Long savedId = restaurantService.addCategory(id, category);
        return ResponseEntity.ok().body(savedId);
    }


}
