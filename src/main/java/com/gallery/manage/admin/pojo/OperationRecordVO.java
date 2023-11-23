package com.gallery.manage.admin.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.light.core.utils.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class OperationRecordVO implements Serializable {

    private String id;

    private String relatedId;

    private String module;

    private String description;

    private Long operationUserId;

    private Byte relatedType;

    private String ip;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date createTime;

    @JSONField(format = DateUtils.DATE_TIME_FORMAT)
    private Date updateTime;

    private String username;




}
