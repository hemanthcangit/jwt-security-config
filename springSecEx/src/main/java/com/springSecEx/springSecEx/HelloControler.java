package com.springSecEx.springSecEx;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControler {
    @GetMapping("/")
    public String Hello(HttpServletRequest request){
        return "welcome"+request.getSession().getId();
    }
}
