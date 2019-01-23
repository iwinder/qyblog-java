package com.windcoder.qycms.core.basis.comment.console;

import com.windcoder.qycms.core.basis.comment.dto.SystemCommentSettingDto;
import com.windcoder.qycms.core.basis.comment.entity.SystemCommentSetting;
import com.windcoder.qycms.core.basis.comment.service.SystemCommentSettingService;
import com.windcoder.qycms.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments/sysCommentSetting")
public class SysCommentSettingController {
    @Autowired
    private SystemCommentSettingService sysCommentService;

    @ModelAttribute(name = "sysComment")
    public SystemCommentSetting getComment(@PathVariable(value = "id",required = false) Long id) {
        if (null == id) {
            return new SystemCommentSetting();
        }
        return sysCommentService.findOne(id);
    }

    @GetMapping(value = "/{id}")
    public SystemCommentSettingDto getSysForum(@PathVariable("id") Long id) {
        SystemCommentSetting sysComment = sysCommentService.findSysForumSetting();
        return ModelMapperUtils.map(sysComment, SystemCommentSettingDto.class);
    }

    /**
     * 修改
     * @param sysComment
     * @return
     */
    @PostMapping(value = "{id}")
    public SystemCommentSettingDto setting(@ModelAttribute("sysForum")SystemCommentSetting sysComment) {
        sysComment = sysCommentService.save(sysComment);
        return ModelMapperUtils.map(sysComment, SystemCommentSettingDto.class);
    }
}
