package com.gallery.manage.admin.controller;

import com.gallery.manage.admin.controller.base.BaseController;
import com.gallery.manage.admin.pojo.RoleVO;
import com.gallery.manage.admin.pojo.SysUserAddPageVO;
import com.gallery.manage.admin.pojo.SysUserRoleVO;
import com.gallery.manage.admin.pojo.SysUserVO;
import com.gallery.manage.admin.service.AdminSysUserService;
import com.gallery.manage.common.aop.annotations.Operation;
import com.gallery.manage.common.model.Role;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUserRole;
import com.gallery.manage.common.service.RoleService;
import com.gallery.manage.common.service.SysUserRoleService;
import com.gallery.manage.common.util.SystemUtil;
import com.github.pagehelper.PageInfo;
import com.light.config.annotations.NeedAdminLogin;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/49kj/admin/sysUser")
@ConditionalOnProperty(value = "manage.enable", havingValue = "true")
public class AdminSysUserController extends BaseController {

    @Autowired
    AdminSysUserService adminSysUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    SysUserRoleService sysUserRoleService;


    @RequestMapping("/list")
    @RequiresPermissions({"sysUser.select"})
    public CommonResult<List<SysUserVO>> listSysUser(HttpServletRequest request, Page page, SysUser param) {
        return adminSysUserService.listSysUser(page, param);
    }

    @RequestMapping("/getSysUserDetail/{id}")
    @RequiresPermissions({"sysUser.update"})
    public CommonResult<SysUserVO> getSysUserDetail(@PathVariable("id") Long id, HttpServletRequest request) {
        return adminSysUserService.getSysUserDetail(id);
    }


    @RequestMapping("/update")
    @RequiresPermissions({"sysUser.update"})
    @Operation(module = "系统用户", description = "修改")
    @NeedAdminLogin
    public CommonResult updateSysUser(Long id, SysUser sysUser) {
        return adminSysUserService.updateSysUser(sysUser);

    }

    @RequestMapping("/listRole")
    @RequiresPermissions({"sysUser.add"})
    public CommonResult<List<RoleVO>> listRole(HttpServletRequest request) {
        return adminSysUserService.listRole();
    }

    @RequestMapping("/save")
    @RequiresPermissions({"sysUser.add"})
    @Operation(module = "系统用户", description = "添加")
    @NeedAdminLogin
    public CommonResult saveUser(SysUser sysUser, SysUserRole userRole) {
        return adminSysUserService.saveSysUser(sysUser, userRole);
    }

    @RequestMapping("/delete/{id}")
    @RequiresPermissions({"sysUser.delete"})
    @Operation(module = "系统用户", description = "删除")
    @NeedAdminLogin
    public CommonResult deleteSysUser(@PathVariable("id") Long id) {
        return adminSysUserService.deleteSysUser(id);
    }


    @RequestMapping("/role/list")
    @RequiresPermissions({"sysUser.role.select"})
    public CommonResult<List<SysUserRoleVO>> listUserRole(SysUserRole param, Page page, HttpServletRequest request) {
        return adminSysUserService.listSysUserRole(param, page);
    }


    @RequestMapping("/auth")
    @RequiresPermissions({"sysUser.role.update"})
    @Operation(module = "用户角色", description = "修改")
    @NeedAdminLogin
    public CommonResult authSysUser(Long id, SysUserRole userRole) {
        Long roleId = userRole.getRoleId();
        Role role = roleService.getById(roleId);
        if (SystemUtil.getAdminRole().equals(role.getRoleCode())) {
            return CommonResult.businessWrong("您无权进行此操作");
        }
        boolean result = adminSysUserService.authSysUser(userRole);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @RequestMapping("/changePassword")
    @NeedAdminLogin
    public CommonResult changePassword(String oldPassword, String newPassword) {
        return adminSysUserService.changePassword(oldPassword, newPassword);
    }

    @RequestMapping("/googleAuthQrCode")
    @RequiresPermissions({"sysUser.verifyCode"})
    @Operation(module = "系统用户", description = "获取登录验证码")
    public void googleAuthQrCode(String username,HttpServletRequest request, HttpServletResponse response) {
        adminSysUserService.generateGoogleAuthQrCode(username,request, response);
    }

}
