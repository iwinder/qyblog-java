package com.windcoder.qycms.system;

import com.windcoder.qycms.basis.TestService.RediesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private RediesService rediesService;

    @GetMapping("sayHello")
    public String sayHello (String name){
        return "Hello " + name;
    }




    @RequestMapping("/set")
    public String set(String key,String value){
        rediesService.setKey(key, value);
        return "success: key = " + key + ", value = " + value;
    }
    @RequestMapping("/get")
    public String get(String key){
        return rediesService.getValue(key);
    }
}
