package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.service.YoutuberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class YoutuberController {
    private final YoutuberService youtuberService;

    @GetMapping("/youtubers")
    public String list(Model model) {
        List<YoutuberDTO> youtubers = youtuberService.getYoutubers();
        model.addAttribute("youtubers", youtubers);
        return "youtubers/youtuberList";
    }

}
