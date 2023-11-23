package com.gallery.manage.admin.pojo;


import com.gallery.manage.common.model.Menu;
import com.gallery.manage.common.util.MenuUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class PermissionDetailVO implements Serializable {

    private List<Long> hasMenuList=new ArrayList<>();

    private List<String> hasPermissions = new ArrayList<>();

    private Set<Menu> menuList = MenuUtil.getMenuList();

    private String roleCode;

    private Long roleId;

    private String adminRole;


}
