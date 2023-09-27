package com.windcoder.qycms.system.admin.controller;

import com.windcoder.qycms.system.entity.ShortLink;
import com.windcoder.qycms.system.dto.ShortLinkDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.dto.ResponseDto;
import com.windcoder.qycms.system.service.ShortLinkService;
import com.windcoder.qycms.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/admin/shortLink")
@Slf4j
public class ShortLinkController {

    @Resource
    private ShortLinkService shortLinkService;

    public static final String BUSINESS_NAME = "短链接表";

    /**
     * 列表查询
     * @param pageDto
     * @return
     */
    @GetMapping("")
    public ResponseDto list(PageDto pageDto) {
        shortLinkService.list(pageDto);
        ResponseDto responseDto = new ResponseDto(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     * @param shortLinkDto
     * @return
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody  ShortLinkDto shortLinkDto) {
        // 保存校验
        ValidatorUtil.length(shortLinkDto.getIdentifier(), "短链接", 1, 255);
        ValidatorUtil.length(shortLinkDto.getUrl(), "链接地址", 1, 2048);
        ValidatorUtil.length(shortLinkDto.getDescription(), "描述", 1, 255);

        shortLinkService.save(shortLinkDto);
        ResponseDto responseDto = new ResponseDto(shortLinkDto);
        return responseDto;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleted")
    public ResponseDto delete(@RequestBody Long[] ids) {
        shortLinkService.delete(ids);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
