package com.windcoder.qycms.core.system.service;

import com.windcoder.qycms.core.system.entity.User;
import com.windcoder.qycms.core.system.repository.UserRepository;
import com.windcoder.qycms.core.system.shiro.PasswordHelper;
import com.windcoder.qycms.service.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

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

    public Page<User> findAll(User user,Pageable pageable){
        return super.findAll((root, query,  cb) -> {
            Predicate predicate = cb.equal(root.get("isDeleted"), false);
//            predicate =  cb.and(predicate,cb.equal(root.get("site").get("id"), ugcActivity.getSite().getId()));
            if(user.getUsername() != null) {
                predicate = cb.and(predicate, cb.like( cb.lower(root.get("title")),
                        "%"+StringUtils.trim(user.getUsername()).toLowerCase()+"%" ));
            }
//            if(user.getIsPublished() != null) {
//                predicate  = cb.and(predicate, cb.equal(root.get("isPublished"),ugcActivity.getIsPublished()));
//            }
            return predicate;

        },pageable);
    }
}
