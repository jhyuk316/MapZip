package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.service.YoutuberService;
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
public class YoutuberController {
    private final YoutuberService youtuberService;

    @GetMapping("/youtubers")
    public String list(Model model) {
        List<YoutuberDTO> youtubers = youtuberService.getYoutubers();
        model.addAttribute("youtubers", youtubers);
        return "youtubers/youtuberList";
    }

    @GetMapping("/youtubers/new")
    public String create(Model model) {
        model.addAttribute("youtuberForm", new YoutuberDTO());
        return "youtubers/createYoutuberForm";
    }

    @PostMapping("/youtubers/new")
    public String create(@Valid @ModelAttribute("youtuberForm") YoutuberDTO youtuberDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "youtubers/createYoutuberForm";
        }

        youtuberService.save(youtuberDTO);
        return "redirect:/";
    }

    @GetMapping("/youtubers/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        YoutuberDTO youtuberDTO = youtuberService.getYoutuberWithRestaurant(id);
        model.addAttribute("youtuber", youtuberDTO);
        return "/youtubers/youtuberDetail";
    }


}
