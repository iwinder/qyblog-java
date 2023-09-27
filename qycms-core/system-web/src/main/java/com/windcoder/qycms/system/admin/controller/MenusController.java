package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.dto.MenusAgentDto;
import com.windcoder.qycms.system.dto.MenusPageDto;
import com.windcoder.qycms.system.entity.Menus;
import com.windcoder.qycms.system.dto.MenusDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.MenusService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/menus")
@Slf4j
public class MenusController {

    @Resource
    private MenusService menusService;

    public static final String BUSINESS_NAME = "菜单表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(MenusPageDto pageDto) {
        ValidatorUtil.require(pageDto.getTargetId(),"关键参数");
        menusService.listWithChildren(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param menusDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  MenusDto menusDto) {
        // 保存校验
        ValidatorUtil.length(menusDto.getName(), "菜单名称", 1, 255);
        ValidatorUtil.length(menusDto.getUrl(), "菜单地址", 1, 255);

        menusService.save(menusDto);
        ResponseDto responseDto = new ResponseDto(menusDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        menusService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }


    @GetMapping("/{menusId}")
    public ResponseDto get(@PathVariable("menusId") Long menusId) {
        MenusDto menusDto = menusService.findOneMenusAgentDto(menusId);
        ResponseDto responseDto = new ResponseDto(menusDto);
        return responseDto;
    }


    @GetMapping("/parent")
    public ResponseDto getParentList(MenusPageDto pageDto) {
        ValidatorUtil.require(pageDto.getTargetId(),"关键参数");
        List<MenusDto> menusDto = menusService.findParentList(pageDto.getTargetId());
        ResponseDto responseDto = new ResponseDto(menusDto);
        return responseDto;
    }
}
