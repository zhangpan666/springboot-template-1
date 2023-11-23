package com.gallery.manage.admin.service;

import com.gallery.manage.admin.pojo.*;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.model.Role;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;

import java.util.List;

public interface AdminSystemService {

    CommonResult<List<RoleVO>> listRole(Page page, Role param);

    CommonResult<PermissionDetailVO> getRolePermissionDetail(Long id);

    boolean saveRole(Role role);

    boolean deleteRole(Long id);

    CommonResult savePermission(AdminPermissionVO permissions);

    Role getRoleById(Long id);

    void updateRole(Role role);

    CommonResult<List<ConfigurationVO>> listConfiguration(Page page, Configuration param);

    CommonResult<ConfigurationVO> getConfigurationById(Long id);

    CommonResult updateConfigurationById(Configuration configuration);

    CommonResult<Object> addConfiguration(Configuration configuration);

    boolean deleteConfigurationById(Long id);

    CommonResult<List<OperationRecordVO>> listOperationRecord(Page page, OperationRecord param);

}
