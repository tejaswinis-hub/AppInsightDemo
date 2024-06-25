package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcom")
public class AppInsightController {
    @GetMapping(path = "/home")
    public String say() {
        return "Welcome to java";
    }
}
