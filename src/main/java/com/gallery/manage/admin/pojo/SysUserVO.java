package com.gallery.manage.admin.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.light.core.utils.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysUserVO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String headImg;

    private Byte accountType;

    private String mobile;

    private Byte gender;

    private String wechatAccount;

    private String wechatUnionId;

    private String wechatNickname;

    private String loginIp;

    private String registerIp;

    private String deviceId;

    private Date lastLoginTime;

    private Date recentLoginTime;

    private String mobileModel;

    private String eMail;

    private Byte osType;

    private Byte status;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date createTime;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date updateTime;

    private String roleName;

    private Long roleId;

    private Byte online;


}
