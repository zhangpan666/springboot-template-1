package com.gallery.manage.common.service;

import com.gallery.manage.admin.pojo.SysUserRoleVO;
import com.gallery.manage.common.model.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    SysUserRole getUserRoleDetailByUserId(Long userId);

    List<SysUserRole> query(SysUserRole sysUserRole);
}
