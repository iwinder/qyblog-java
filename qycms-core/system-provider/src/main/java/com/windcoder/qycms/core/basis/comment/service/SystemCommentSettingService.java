package com.windcoder.qycms.core.basis.comment.service;

import com.windcoder.qycms.core.basis.comment.entity.SystemCommentSetting;
import com.windcoder.qycms.core.basis.comment.repository.jpa.SystemCommentSettingRepository;
import com.windcoder.qycms.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class SystemCommentSettingService extends BaseService<SystemCommentSetting, Long, SystemCommentSettingRepository> {
    public SystemCommentSetting findSysForumSetting() {
        return findOne(1L);
    }
}
