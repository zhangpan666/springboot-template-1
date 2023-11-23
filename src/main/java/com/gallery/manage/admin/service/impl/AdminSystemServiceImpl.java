package com.gallery.manage.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.gallery.manage.admin.pojo.*;
import com.gallery.manage.admin.service.AdminSystemService;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.*;
import com.gallery.manage.common.service.*;
import com.gallery.manage.common.util.MenuUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.github.pagehelper.PageInfo;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;
import com.light.core.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AdminSystemServiceImpl implements AdminSystemService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    OperationRecordService operationRecordService;

    @Autowired
    CacheService cacheService;


    @Override
    public CommonResult<List<RoleVO>> listRole(Page page, Role param) {
        PageInfo<Role> pageInfo = PageUtil.query(page, param, roleService::query);
        List<Role> list = pageInfo.getList();
        List<RoleVO> roleVOList = list.stream().map(role -> {
            RoleVO roleVO = new RoleVO().setId(role.getId()).setRoleCode(role.getRoleCode()).setRoleName(role.getRoleName())
                    .setCreateTime(role.getCreateTime()).setUpdateTime(role.getUpdateTime());
            return roleVO;
        }).collect(Collectors.toList());
        return CommonResult.success(roleVOList);
    }

    @Override
    public CommonResult<PermissionDetailVO> getRolePermissionDetail(Long id) {
        PermissionDetailVO permissionDetailVO = new PermissionDetailVO();
        Role role = roleService.getById(id);
        List<Long> hasMenuList = new ArrayList<>();
        List<String> hasPermissions = new ArrayList<>();
        Set<Menu> menuList = MenuUtil.getMenuList();
        if (role != null) {
            List<Permission> permissionList = permissionService.getPermissionListByRoleId(id);
            if (!CollectionUtils.isEmpty(permissionList)) {
                permissionList.stream().forEach(permission -> {
                    Long menuId = permission.getMenuId();
                    String[] permissionArray = permission.getPermission().split(",");
                    hasMenuList.add(menuId);
                    for (String permissionStr : permissionArray) {
                        hasPermissions.add(permissionStr);
                    }
                });
            }
            permissionDetailVO.setRoleCode(role.getRoleCode()).setRoleId(role.getId());
        }
        permissionDetailVO.setMenuList(menuList).setHasMenuList(hasMenuList).setHasPermissions(hasPermissions)
                .setAdminRole(SystemUtil.getAdminRole());
        return CommonResult.success(permissionDetailVO);
    }

    @Override
    public boolean saveRole(Role role) {
        return roleService.save(role);
    }

    @Override
    public boolean deleteRole(Long id) {

        boolean removeById = roleService.removeById(id);
        if (removeById) {
            cacheService.clearSysUserAuthorization();
        }
        return removeById;

    }

    @Override
    public CommonResult savePermission(AdminPermissionVO adminPermissionVO) {
        Long roleId = adminPermissionVO.getRoleId();
        String permissions = adminPermissionVO.getPermissionList();
        List<Permission> permissionList = JSON.parseArray(permissions, Permission.class);
        Role role = roleService.getById(roleId);
        if (role == null || role.getRoleCode().equals(SystemUtil.getAdminRole())) {
            return CommonResult.businessWrong();
        }
        permissionService.deleteByRoleId(roleId);
        if (!CollectionUtils.isEmpty(permissionList)) {
            permissionService.saveBatch(permissionList, 10);
        }
        cacheService.clearSysUserAuthorization();
        return CommonResult.success();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleService.getById(id);
    }

    @Override
    public void updateRole(Role role) {
        roleService.updateById(role);
        cacheService.clearSysUserAuthorization();
    }

    @Override
    public CommonResult<List<ConfigurationVO>> listConfiguration(Page page, Configuration param) {
        param.setDisplay(ProjectConstant.COMMON_STATUS_AVAILABLE);
        PageInfo<Configuration> pageInfo = PageUtil.query(page, param, configurationService::query);
        List<Configuration> list = pageInfo.getList();
        List<ConfigurationVO> configurationVOList = list.stream().map(configuration -> {
            ConfigurationVO configurationVO = new ConfigurationVO().setId(configuration.getId()).setName(configuration.getName()).setCode(configuration.getCode())
                    .setValue(configuration.getValue()).setStatus(configuration.getStatus()).setCreateTime(configuration.getCreateTime())
                    .setUpdateTime(configuration.getUpdateTime());
            return configurationVO;
        }).collect(Collectors.toList());
        return CommonResult.success(configurationVOList);
    }

    @Override
    public CommonResult<ConfigurationVO> getConfigurationById(Long id) {
        Configuration configuration = configurationService.getById(id);
        if (configuration==null){
            return CommonResult.businessWrong("该常量不存在");
        }
        ConfigurationVO configurationVO = new ConfigurationVO().setId(configuration.getId()).setName(configuration.getName()).setCode(configuration.getCode())
                .setValue(configuration.getValue()).setStatus(configuration.getStatus()).setCreateTime(configuration.getCreateTime())
                .setUpdateTime(configuration.getUpdateTime());
        return CommonResult.success(configurationVO);
    }

    @Override
    public CommonResult updateConfigurationById(Configuration configuration) {
        String code = configuration.getCode();
        Configuration configurationServiceByCode = configurationService.getByCode(code);
        if (configurationServiceByCode != null && !configurationServiceByCode.getId().equals(configuration.getId())) {
            return CommonResult.businessWrong("该常量已存在");
        }
        boolean update = configurationService.updateById(configuration);
        if (update) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public CommonResult<Object> addConfiguration(Configuration configuration) {
        String code = configuration.getCode();
        Configuration configurationServiceByCode = configurationService.getByCode(code);
        if (configurationServiceByCode != null) {
            return CommonResult.businessWrong("该常量已存在");
        }
        boolean save = configurationService.save(configuration);
        if (save) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public boolean deleteConfigurationById(Long id) {
        return configurationService.removeById(id);
    }

    @Override
    public CommonResult<List<OperationRecordVO>> listOperationRecord(Page page, OperationRecord param) {
        PageInfo<OperationRecord> pageInfo = PageUtil.query(page, param, operationRecordService::query);
        List<OperationRecord> list = pageInfo.getList();
        List<OperationRecordVO> operationRecordVOList = list.stream().map((Function<OperationRecord, OperationRecordVO>) operationRecord -> {
            new OperationRecordVO().setId(operationRecord.getId()).setCreateTime(operationRecord.getCreateTime())
                    .setDescription(operationRecord.getDescription()).setIp(operationRecord.getIp()).setModule(operationRecord.getModule())
                    .setOperationUserId(operationRecord.getOperationUserId()).setRelatedId(operationRecord.getRelatedId()).setRelatedType(operationRecord.getRelatedType())
                    .setUpdateTime(operationRecord.getUpdateTime()).setUsername(operationRecord.getUsername());
            return null;
        }).collect(Collectors.toList());
        return CommonResult.success(operationRecordVOList);
    }

}
