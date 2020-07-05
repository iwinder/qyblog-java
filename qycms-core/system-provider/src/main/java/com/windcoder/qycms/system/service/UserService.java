package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.entity.UserExample;
import com.windcoder.qycms.system.dto.UserDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.UserMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

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
        List<UserDto> userDtoList = CopyUtil.copyList(users, UserDto.class);
        pageDto.setList(userDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param userDto
     */
    public void save(UserDto userDto){
        User user = CopyUtil.copy(userDto, User.class);
        if (null == user.getId()) {
            this.inster(user);
        } else {
            this.update(user);
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
        userMapper.updateByPrimaryKey(user);
    }

}
