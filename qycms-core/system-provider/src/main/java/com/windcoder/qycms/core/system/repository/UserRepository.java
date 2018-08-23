package com.windcoder.qycms.core.system.repository;

import com.windcoder.qycms.core.system.entity.User;

public interface UserRepository extends SupportRepository<User,Long>  {
    User findByUsername(String username);
}
