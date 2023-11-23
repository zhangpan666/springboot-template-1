package com.gallery.manage.admin.controller;

import com.gallery.manage.admin.controller.base.BaseController;
import com.gallery.manage.admin.pojo.*;
import com.gallery.manage.admin.service.AdminSystemService;
import com.gallery.manage.common.aop.annotations.Operation;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.model.Role;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/shortVideo/admin/system")
@ConditionalOnProperty(value = "manage.enable", havingValue = "true")
public class AdminSystemController extends BaseController {

    @Autowired
    AdminSystemService adminSystemService;


    @RequestMapping("/role/list")
    @RequiresPermissions({"role.select"})
    public CommonResult<List<RoleVO>> listRole(Page page, Role param, HttpServletRequest request) {
        return adminSystemService.listRole(page, param);
    }


    @RequestMapping("/role/add")
    @RequiresPermissions({"role.add"})
    @Operation(module = "角色", description = "添加")
    @NeedAdminLogin
    public CommonResult saveRole(Role role) {
        boolean saveRole = adminSystemService.saveRole(role);
        if (saveRole) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @RequestMapping("/role/getRoleDetail/{id}")
    @RequiresPermissions({"role.update"})
    public CommonResult<RoleVO> getRoleDetail(@PathVariable("id") Long id, HttpServletRequest request) {
        Role role = adminSystemService.getRoleById(id);
        RoleVO roleVO = new RoleVO().setId(role.getId()).setRoleCode(role.getRoleCode()).setRoleName(role.getRoleName())
                .setCreateTime(role.getCreateTime()).setUpdateTime(role.getUpdateTime());
        return CommonResult.success(roleVO);
    }

    @RequestMapping("/role/update")
    @ResponseBody
    @RequiresPermissions({"role.update"})
    @Operation(module = "角色", description = "修改")
    @NeedAdminLogin
    public CommonResult updateRole(Long id, Role role, HttpServletRequest request) {
        adminSystemService.updateRole(role);
        return CommonResult.success();
    }

    @ResponseBody
    @RequestMapping("/role/delete/{id}")
    @RequiresPermissions({"role.delete"})
    @Operation(module = "角色", description = "删除")
    @NeedAdminLogin
    public CommonResult deleteRole(@PathVariable("id") Long id) {
        boolean deleteRole = adminSystemService.deleteRole(id);
        if (deleteRole) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }


    @RequestMapping("/role/getRolePermissionDetail/{id}")
    @RequiresPermissions({"role.select"})
    public CommonResult<PermissionDetailVO> getRolePermissionDetail(@PathVariable("id") Long id, HttpServletRequest request) {
        return adminSystemService.getRolePermissionDetail(id);
    }


    @RequestMapping("/permission/save")
    @ResponseBody
    @RequiresPermissions({"role.add"})
    @Operation(module = "角色", description = "权限设置")
    @NeedAdminLogin
    public CommonResult savePermission(Long roleId, AdminPermissionVO adminPermissionVO, HttpServletRequest request) {
        return adminSystemService.savePermission(adminPermissionVO);
    }

    @RequestMapping("/configuration/list")
    @RequiresPermissions({"configuration.select"})
    public CommonResult<List<ConfigurationVO>> listConfiguration(Page page, Configuration param, HttpServletRequest request) {
        return adminSystemService.listConfiguration(page, param);
    }


    @RequestMapping("/configuration/add")
    @ResponseBody
    @Operation(module = "常量配置", description = "添加")
    @RequiresPermissions({"configuration.add"})
    @NeedAdminLogin
    public CommonResult addConfiguration(Configuration configuration, HttpServletRequest request) {
        return adminSystemService.addConfiguration(configuration);
    }


    @RequestMapping("/configuration/getUpdatePage/{id}")
    @RequiresPermissions({"configuration.update"})
    public CommonResult<ConfigurationVO> getConfigurationUpdatePage(@PathVariable("id") Long id) {
        return adminSystemService.getConfigurationById(id);
    }


    @RequestMapping("/configuration/update")
    @ResponseBody
    @Operation(module = "常量配置", description = "修改")
    @RequiresPermissions({"configuration.update"})
    @NeedAdminLogin
    public CommonResult updateConfiguration(Long id, Configuration configuration, HttpServletRequest request) {
        return adminSystemService.updateConfigurationById(configuration);
    }

    @RequestMapping("/configuration/delete/{id}")
    @ResponseBody
    @Operation(module = "常量配置", description = "删除")
    @RequiresPermissions({"configuration.delete"})
    @NeedAdminLogin
    public CommonResult deleteConfiguration(@PathVariable("id") Long id, HttpServletRequest request) {
        boolean result = adminSystemService.deleteConfigurationById(id);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }


    @RequestMapping("/operationRecord/list")
    @RequiresPermissions({"operationRecord.select"})
    public CommonResult<List<OperationRecordVO>> listOperationRecord(Page page, OperationRecord param, HttpServletRequest request) {
        return adminSystemService.listOperationRecord(page, param);
    }



}
