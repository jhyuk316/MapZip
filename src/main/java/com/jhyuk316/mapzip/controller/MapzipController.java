package com.jhyuk316.mapzip.controller;

import lombok.extern.slf4j.Slf4j;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jhyuk316.mapzip.service.*;
import com.jhyuk316.mapzip.dto.*;
import com.jhyuk316.mapzip.model.*;

@Slf4j
@RestController
@RequestMapping("mapzip")
public class MapzipController {
    @Autowired
    private MapzipService service;

    @GetMapping("/test")
    public ResponseEntity<?> testMapzip() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        log.info(str);
        log.info("test");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/findRestaurant")
    public ResponseEntity<?> getRestaurantList(@RequestParam(required = true) String name) {
        log.info(name);
        List<RestaurantEntity> entities = service.getListRestaurant(name);

        List<MapzipDTO> dtos = entities.stream().map(MapzipDTO::new).collect(Collectors.toList());

        ResponseDTO<MapzipDTO> response = ResponseDTO.<MapzipDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allRestaurant")
    public ResponseEntity<?> getAllRestaurant() {
        List<RestaurantEntity> entities = service.getAllRestaurant();

        List<MapzipDTO> dtos = entities.stream().map(MapzipDTO::new).collect(Collectors.toList());

        ResponseDTO<MapzipDTO> response = ResponseDTO.<MapzipDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/allYoutuber")
    public ResponseEntity<?> getAllYoutuber() {
        List<YoutuberEntity> entities = service.getAllYoutuber();

        List<YoutuberDTO> dtos =
                entities.stream().map(YoutuberDTO::new).collect(Collectors.toList());

        ResponseDTO<YoutuberDTO> response = ResponseDTO.<YoutuberDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }



}
