package com.jhyuk316.mapzip.api;

import com.jhyuk316.mapzip.service.YoutuberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class YoutuberApiController {
    private final YoutuberService youtuberService;

}
