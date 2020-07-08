package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.dto.UserInfoDto;
import com.windcoder.qycms.system.entity.User;
import com.windcoder.qycms.system.dto.UserDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.UserService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    public static final String BUSINESS_NAME = "用户";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        userService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param userDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  UserDto userDto) {
        // 保存校验
        ValidatorUtil.require(userDto.getUsername(), "用户名");
        ValidatorUtil.length(userDto.getUsername(), "用户名", 1, 50);
        ValidatorUtil.length(userDto.getNickname(), "昵称", 1, 50);
        if (userDto!=null && userDto.getId() == null) {
            ValidatorUtil.require(userDto.getPassword(), "密码");
            ValidatorUtil.length(userDto.getPassword(), "密码", 1, 255);
        }
        ValidatorUtil.length(userDto.getAvatar(), "用户头像", 1, 255);

        userService.save(userDto);
        ResponseDto responseDto = new ResponseDto(userDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        userService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }



    @GetMapping("/{userId}")
    public ResponseDto get(@PathVariable("userId") Long userId) {
        UserInfoDto article = userService.findOneUserDto(userId);
        ResponseDto responseDto = new ResponseDto(article);
        return responseDto;
    }
}
