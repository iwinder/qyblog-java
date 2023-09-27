package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.dto.UserInfoDto;
import com.windcoder.qycms.system.entity.MenusAgent;
import com.windcoder.qycms.system.dto.MenusAgentDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.MenusAgentService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/menusAgent")
@Slf4j
public class MenusAgentController {

    @Resource
    private MenusAgentService menusAgentService;

    public static final String BUSINESS_NAME = "菜单包装表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        menusAgentService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param menusAgentDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  MenusAgentDto menusAgentDto) {
        // 保存校验
        ValidatorUtil.length(menusAgentDto.getName(), "菜单组名称", 1, 255);

        menusAgentService.save(menusAgentDto);
        ResponseDto responseDto = new ResponseDto(menusAgentDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        menusAgentService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }

    @GetMapping("/{agentId}")
    public ResponseDto get(@PathVariable("agentId") Long agentId) {
        MenusAgentDto menusAgentDto = menusAgentService.findOneMenusAgentDto(agentId);
        ResponseDto responseDto = new ResponseDto(menusAgentDto);
        return responseDto;
    }
}
