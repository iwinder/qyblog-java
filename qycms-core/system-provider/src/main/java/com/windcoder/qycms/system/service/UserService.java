package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.entity.GlobalProperties;
import com.windcoder.qycms.system.dto.*;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.entity.UserExample;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.MyUserMapper;
import com.windcoder.qycms.system.repository.mybatis.UserMapper;
import com.windcoder.qycms.system.shiro.PasswordHelper;
import com.windcoder.qycms.utils.CopyUtil;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MyUserMapper myUserMapper;
    @Autowired
    private GlobalProperties globalProperties;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserInfoDto> userDtoList = CopyUtil.copyList(users, UserInfoDto.class);
        pageDto.setList(userDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param userDto
     */
    @Transactional
    public void save(UserDto userDto){
        Long roleId = userDto.getRoleId();
        User user = ModelMapperUtils.map(userDto, User.class);
        if (null == user.getId()) {
            this.inster(user);
        } else {
            this.update(user);
        }
        if(roleId!=null) {
            permissionService.updateUserRole(user.getId(),roleId);
        }

    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(Arrays.asList(ids));
        userMapper.deleteByExample(userExample);
    }

    /**
     * 新增
     * @param user
     */
    private void inster(User user){
        Date now = new Date();
        user.setCreatedDate(now);
        user.setLastModifiedDate(now);
        userMapper.insert(user);
    }

    /**
     * 更新
     * @param user
     */
    private void update(User user){
        user.setLastModifiedDate(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        Map<Object, Object> oldUser =  findSiteAdminUserInfo();
        if (oldUser.get("id").equals(user.getId()+"")) {
            oldUser.put("nickname", user.getNickname());
            oldUser.put("avatar", user.getAvatar());
            refreshSiteAdminUserInfo(oldUser);
        }
    }
    public User findOne(Long userId) {
       return userMapper.selectByPrimaryKey(userId);
    }

    public UserInfoDto findOneUserDto(Long userId) {
        User user = findOne(userId);
        UserInfoDto userInfo =  ModelMapperUtils.map(user, UserInfoDto.class);
        Long roleId =  permissionService.selectRoleIdByUserId(userInfo.getId());
        userInfo.setRoleId(roleId);
        return userInfo;
    }

    public UserWebDto findOneUserWebDto(Long userId) {
        User user = findOne(userId);
        UserWebDto userWebDto = ModelMapperUtils.map(user, UserWebDto.class);
        return userWebDto;
    }

    public User findByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public Map<Object, Object> findSiteAdminUserInfo() {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        Map<Object, Object> userInfo  = ops.entries("siteInfo:adminUser");
        if (userInfo == null || userInfo.isEmpty()) {
            userInfo  =  myUserMapper.findSiteAdminUserInfo();
            if (userInfo!=null) {
                userInfo.put("id",userInfo.get("id") + "");
            }
            ops.putAll("siteInfo:adminUser", userInfo);
        }
        return userInfo;
    }
    @Async
    public void  refreshSiteAdminUserInfo(Map<Object, Object> userInfo) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.putAll("siteInfo:adminUser", userInfo);
    }

    public Map<Object, Object>  currentUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return myUserMapper.findUserAndRoleInfoById(user.getId());
    }
    public void entryptPassword(User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            String salt = PasswordHelper.generateSalt();
            user.setSalt(salt);
            String password = PasswordHelper.encryptPassword(user,globalProperties.getToken());
            user.setPassword(password);
        }
    }
}
