package com.windcoder.qycms.core.system.service;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.core.system.repository.UserRepository;
import com.windcoder.qycms.core.system.shiro.PasswordHelper;
import com.windcoder.qycms.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User,Long,UserRepository> {

    private void entryptPassword(User user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            String salt = PasswordHelper.generateSalt();
            user.setSalt(salt);
            String password = PasswordHelper.encryptPassword(user);
            user.setPassword(password);
        }
    }
    public User save(User user){
        entryptPassword(user);
        return super.save(user);
    }

    public User update(User user) {
        return super.save(user);
    }

    public User findByUsername(String username){
        return repository.findByUsername(username);
    }
}
