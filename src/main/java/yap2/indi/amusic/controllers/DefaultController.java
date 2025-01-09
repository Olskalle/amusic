package yap2.indi.amusic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String getDefaultPage() {
        return "redirect:/artists";
    }
}
