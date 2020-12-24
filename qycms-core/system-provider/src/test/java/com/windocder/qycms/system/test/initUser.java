package com.windocder.qycms.system.test;

import com.windcoder.qycms.entity.GlobalProperties;
import com.windcoder.qycms.QycmsApplication;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.shiro.PasswordHelper;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QycmsApplication.class)
public class initUser {
    @Autowired
    private GlobalProperties globalProperties;
    @Test
    public void initUser() {
        User user  = new User();
        user.setUsername("WindZRCQY");
        user.setPassword("K@GT&30EVkVk");
        user.setSalt(PasswordHelper.generateSalt());
        user.setPassword( PasswordHelper.encryptPassword(user,globalProperties.getToken()));
//        userService.entryptPassword(user);
        log.error("User Salt:" + user.getSalt() + " Password:"+ user.getPassword());
    }
}
