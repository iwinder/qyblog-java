package com.windcoder.qycms.core.basis.comment.web;

import com.windcoder.qycms.core.basis.comment.dto.SystemCommentSettingDto;
import com.windcoder.qycms.core.basis.comment.entity.SystemCommentSetting;
import com.windcoder.qycms.core.basis.comment.service.SystemCommentSettingService;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/web/comments/sysCommentSetting")
public class SysCommentSettingWebController {
    @Autowired
    private SystemCommentSettingService sysCommentService;


    @GetMapping(value = "/{id}")
    public SystemCommentSettingDto getSysForum(@PathVariable("id") Long id) {
        SystemCommentSetting sysComment = sysCommentService.findSysForumSetting();
        return ModelMapperUtils.map(sysComment, SystemCommentSettingDto.class);
    }


}
