package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.basis.TestService.RediesService;
import com.windcoder.qycms.core.basis.mqTest.MqTestAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private MqTestAdapter mqTestAdapter;
    @Autowired
    private RediesService rediesService;

    @GetMapping("sayHello")
    public String sayHello (String name){
        return "Hello " + name;
    }

    @GetMapping("sayMq")
    public String sayMq (int i, String name){

        mqTestAdapter.process(i,name);
        return "sayMq:" + i +" ,Messages："+ name;
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
