package com.windcoder.qycms.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.windcoder.qycms.system.entity.Privilege;
import com.windcoder.qycms.system.entity.PrivilegeExample;
import com.windcoder.qycms.system.dto.PrivilegeDto;
import com.windcoder.qycms.dto.PageDto;
import com.windcoder.qycms.system.repository.mybatis.PrivilegeMapper;
import com.windcoder.qycms.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

@Service
public class PrivilegeService {
    @Resource
    private PrivilegeMapper privilegeMapper;

    /**
     * 列表查询
     * @param pageDto
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        PrivilegeExample privilegeExample = new PrivilegeExample();
        List<Privilege> privileges = privilegeMapper.selectByExample(privilegeExample);
        PageInfo<Privilege> pageInfo = new PageInfo<>(privileges);
        pageDto.setTotal(pageInfo.getTotal());
        List<PrivilegeDto> privilegeDtoList = CopyUtil.copyList(privileges, PrivilegeDto.class);
        pageDto.setList(privilegeDtoList);
    }


    /**
     * 保存，id有值时更新，无值时新增
     * @param privilegeDto
     */
    public void save(PrivilegeDto privilegeDto){
        Privilege privilege = CopyUtil.copy(privilegeDto, Privilege.class);
        if (null == privilege.getId()) {
            this.inster(privilege);
        } else {
            this.update(privilege);
        }
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(Long[] ids) {
        PrivilegeExample privilegeExample = new PrivilegeExample();
        privilegeExample.createCriteria().andIdIn(Arrays.asList(ids));
        privilegeMapper.deleteByExample(privilegeExample);
    }

    /**
     * 新增
     * @param privilege
     */
    private void inster(Privilege privilege){
        Date now = new Date();
        privilege.setCreatedDate(now);
        privilege.setLastModifiedDate(now);
        privilegeMapper.insertSelective(privilege);
    }

    /**
     * 更新
     * @param privilege
     */
    private void update(Privilege privilege){
        privilege.setLastModifiedDate(new Date());
        privilegeMapper.updateByPrimaryKeySelective(privilege);
    }

    public Privilege getOne(Long privilegeId) {
        return privilegeMapper.selectByPrimaryKey(privilegeId);
    }
}
