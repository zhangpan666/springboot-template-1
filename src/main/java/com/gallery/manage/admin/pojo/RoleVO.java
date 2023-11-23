package com.gallery.manage.admin.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.light.core.utils.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class RoleVO implements Serializable {

    private Long id;

    private String roleCode;

    private String roleName;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date createTime;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date updateTime;


}
