package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.ResponseDTO;
import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import com.jhyuk316.mapzip.service.MapzipService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v0")
public class MapzipController {

    private final int PAGE_SIZE = 3;

    private final MapzipService service;


    @GetMapping(value = "/restaurants", params = {"latitude", "longitude"})
    public ResponseEntity<ResponseDTO<RestaurantDTO>> nearbyRestaurants(
            @RequestParam(required = true) double latitude,
            @RequestParam(required = true) double longitude, Pageable pageable) {
        List<RestaurantEntity> entities = service.nearbyRestaurants(latitude, longitude);

        List<RestaurantDTO> dtos = entities.stream().map(RestaurantDTO::new)
                .collect(Collectors.toList());

        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder().result(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/restaurants", params = {"name"})
    public ResponseEntity<ResponseDTO<RestaurantDTO>> getRestaurantByName(
            @RequestParam(required = true) String name,
            Pageable pageable) {
        log.info(name);
        List<RestaurantEntity> entities = service.findRestaurants(name);

        List<RestaurantDTO> dtos = entities.stream().map(RestaurantDTO::new)
                .collect(Collectors.toList());

        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder().result(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<ResponseDTO<RestaurantDTO>> getRestaurants(
            @PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        Page<RestaurantEntity> entities = service.getRestaurants(pageable);

        List<RestaurantDTO> dtos = entities.stream().map(RestaurantDTO::new)
                .collect(Collectors.toList());

        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder()
                .page(pageable.getPageNumber())
                .result(dtos)
                .total_pages(pageable.getPageSize())
                .total_result(entities.getTotalElements())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<ResponseDTO<RestaurantDTO>> getRestaurant(
            @PathVariable(required = false) long id) {

        RestaurantDTO dto = service.getRestaurant(id);

        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder()
                .result(List.of(dto))
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/restaurants")
    public ResponseEntity<?> addRestarurant(@RequestBody RestaurantDTO restaurantDTO) {

        List<RestaurantEntity> entities = service.save(restaurantDTO);

        List<RestaurantDTO> dtos = entities.stream().map(RestaurantDTO::new)
                .collect(Collectors.toList());

        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder().result(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    //    @PutMapping("/restaurants")
    //    public ResponseEntity<?> updateRestarurant(@RequestBody RestaurantDTO restaurantDTO) {
    //
    //        List<RestaurantEntity> entities = service.save(restaurantDTO);
    //
    //        List<RestaurantDTO> dtos = entities.stream().map(RestaurantDTO::new).collect(Collectors.toList());
    //
    //        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder().result(dtos).build();
    //
    //        return ResponseEntity.ok().body(response);
    //    }

    //    @DeleteMapping("/restaurants")
    //    public ResponseEntity<?> deleteRestarurant(@RequestParam(required = true) long id) {
    //
    //        List<RestaurantEntity> entities = service.save(restaurantDTO);
    //
    //        List<RestaurantDTO> dtos = entities.stream().map(RestaurantDTO::new).collect(Collectors.toList());
    //
    //        ResponseDTO<RestaurantDTO> response = ResponseDTO.<RestaurantDTO>builder().result(dtos).build();
    //
    //        return ResponseEntity.ok().body(response);
    //    }


    @GetMapping("/youtubers")
    public ResponseEntity<?> getAllYoutuber(Pageable pageable) {
        List<YoutuberEntity> entities = service.getAllYoutuber();

        List<YoutuberDTO> dtos =
                entities.stream().map(YoutuberDTO::new).collect(Collectors.toList());

        ResponseDTO<YoutuberDTO> response = ResponseDTO.<YoutuberDTO>builder().result(dtos).build();

        return ResponseEntity.ok().body(response);
    }

}
