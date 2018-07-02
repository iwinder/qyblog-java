package com.windcoder.qycms.core.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("sayHello")
    public String sayHello (String name){
        return "Hello " + name;
    }
}
