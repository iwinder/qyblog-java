package com.windcoder.qycms.blog.admin.controller;

import com.windcoder.qycms.blog.dto.BlogCategoryDto;
import com.windcoder.qycms.blog.entity.BlogMove;
import com.windcoder.qycms.blog.service.BlogMoveService;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.annotation.CurrentUser;
import com.windcoder.qycms.system.dto.UserWebDto;
import com.windcoder.qycms.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/blogMove")
public class BlogMoveController {
    @Autowired
    private BlogMoveService blogMoveService;

    @PostMapping("/save")
    public ResponseDto save(@RequestBody BlogMove blogMove, @CurrentUser UserWebDto user) {
        // 保存校验
        ValidatorUtil.require(blogMove.getUsername(), "Mysql用户名");
        ValidatorUtil.require(blogMove.getPassword(), "Mysql密码");
        ValidatorUtil.require(blogMove.getPort(), "端口");
        ValidatorUtil.require(blogMove.getPort(), "数据库不能");
        ValidatorUtil.require(blogMove.getIp(), "数据库ip地址");

        blogMoveService.importDataByDB(blogMove,user);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }

}
