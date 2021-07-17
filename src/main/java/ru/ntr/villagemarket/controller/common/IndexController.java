package ru.ntr.villagemarket.controller.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/swagger-ui/";
    }
}
