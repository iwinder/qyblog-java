package com.windcoder.qycms.system.shiro;
import com.windcoder.qycms.system.dto.UserWebDto;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.RedisSerializer;

import java.io.Serializable;

public class UserToken extends ObjectSerializer {
    private UserWebDto user;

    public UserWebDto getUser() {
        return user;
    }
    public void setUser(UserWebDto user) {
        this.user = user;
    }

//    public String getUsername() {
//        return user.getUsername();
//    }
}
