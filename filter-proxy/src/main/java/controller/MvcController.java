package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 자동으로 message convertor로 보냄.
public class MvcController {
    @GetMapping("/hello")
    public String test() {
        return "Hello World";
    }
}
