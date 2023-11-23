package com.gallery.manage.admin.pojo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class SysUserAddPageVO implements Serializable {

    private List<RoleVO> roleList;
}
