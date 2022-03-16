package com.jhyuk316.mapzip.controller;

import lombok.extern.slf4j.Slf4j;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jhyuk316.mapzip.service.*;
import com.jhyuk316.mapzip.dto.*;

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
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

}
