package com.windcoder.qycms.core.system.repository;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.repository.SupportRepository;

public interface UserRepository extends SupportRepository<User, Long> {
    User findByUsername(String username);
}
