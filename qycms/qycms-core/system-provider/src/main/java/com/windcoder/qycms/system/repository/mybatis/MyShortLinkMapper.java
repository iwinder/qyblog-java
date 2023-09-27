package com.windcoder.qycms.system.repository.mybatis;

import com.windcoder.qycms.system.dto.ShortLinkWebDto;

import java.util.List;

public interface MyShortLinkMapper {
    List<ShortLinkWebDto> findAllWebDto();
}
