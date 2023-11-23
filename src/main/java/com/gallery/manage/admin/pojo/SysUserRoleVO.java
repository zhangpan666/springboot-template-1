package com.gallery.manage.admin.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.light.core.utils.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysUserRoleVO implements Serializable {


    private Long id;

    private Long roleId;

    private Byte status;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date createTime;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date updateTime;

    private String roleCode;

    private String roleName;

    private String nickname;

    private String username;

}
