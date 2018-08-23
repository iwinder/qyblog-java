package com.windcoder.qycms.core.system.service;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.core.system.repository.UserRepository;

public class UserService extends BaseService<User,Long, UserRepository>  {

    public User findByUsername(String username){
        return repository.findByUsername(username);
    }
}
