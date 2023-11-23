package com.gallery.manage.admin.pojo;

import com.gallery.manage.common.model.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionVO implements Serializable {

    List<Permission>permissionList;
}
