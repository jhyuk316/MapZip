package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.config.auth.dto.SessionUser;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        // resources/templates/index.mustache가 반환 됨.
        return "index";
    }

    @GetMapping("/members/join")
    public String join() {
        return "member-join";
    }

}
