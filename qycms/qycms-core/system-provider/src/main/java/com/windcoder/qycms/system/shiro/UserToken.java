package com.windcoder.qycms.system.shiro;
import com.windcoder.qycms.system.dto.RoleDto;
import com.windcoder.qycms.system.dto.RoleWebDto;
import com.windcoder.qycms.system.dto.UserWebDto;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.RedisSerializer;

import java.io.Serializable;

public class UserToken extends ObjectSerializer {
    private UserWebDto user;
    private RoleWebDto role;

    public UserWebDto getUser() {
        return user;
    }
    public void setUser(UserWebDto user) {
        this.user = user;
    }

    public RoleWebDto getRole() {
        return role;
    }

    public void setRole(RoleWebDto role) {
        this.role = role;
    }

//    public String getUsername() {
//        return user.getUsername();
//    }
}
