package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.Link;
import com.windcoder.qycms.system.dto.LinkDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.LinkService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/link")
@Slf4j
public class LinkController {

    @Resource
    private LinkService linkService;

    public static final String BUSINESS_NAME = "链接表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        linkService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param linkDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  LinkDto linkDto) {
        // 保存校验
        ValidatorUtil.length(linkDto.getName(), "链接名称", 1, 255);
        ValidatorUtil.length(linkDto.getUrl(), "链接地址", 1, 255);

        linkService.save(linkDto);
        ResponseDto responseDto = new ResponseDto(linkDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        linkService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
