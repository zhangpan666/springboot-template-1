package com.gallery.manage.admin.service;

import com.gallery.manage.admin.pojo.RoleVO;
import com.gallery.manage.admin.pojo.SysUserAddPageVO;
import com.gallery.manage.admin.pojo.SysUserRoleVO;
import com.gallery.manage.admin.pojo.SysUserVO;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUserRole;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AdminSysUserService {

    CommonResult<List<SysUserVO>> listSysUser(Page page, SysUser sysUser);

    CommonResult<SysUserVO> getSysUserDetail(Long id);

    CommonResult updateSysUser(SysUser sysUser);

    CommonResult saveSysUser(SysUser sysUser, SysUserRole userRole);

    CommonResult deleteSysUser(Long id);

    boolean authSysUser(SysUserRole userRole);

    CommonResult changePassword(String oldPassword, String newPassword);

    void generateGoogleAuthQrCode(String username, HttpServletRequest request, HttpServletResponse response) throws RuntimeException;

    CommonResult<List<SysUserRoleVO>> listSysUserRole(SysUserRole param, Page page);

    CommonResult<List<RoleVO>> listRole();
}
