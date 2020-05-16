package com.helix.datatrail.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author lijianyu
 * @date 2019/1/2 14:33
 */
public class DataTrailEntity {

    private Long id;

    private String opsEvent;

    private Date opsTime;

    private String opsObjectName;

    private Long opsObjectId;

    private String opsObjectContent;

    private Date createTime;

    private Date modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpsEvent() {
        return opsEvent;
    }

    public void setOpsEvent(String opsEvent) {
        this.opsEvent = opsEvent;
    }

    public Date getOpsTime() {
        return opsTime;
    }

    public void setOpsTime(Date opsTime) {
        this.opsTime = opsTime;
    }

    public String getOpsObjectName() {
        return opsObjectName;
    }

    public void setOpsObjectName(String opsObjectName) {
        this.opsObjectName = opsObjectName;
    }

    public Long getOpsObjectId() {
        return opsObjectId;
    }

    public void setOpsObjectId(Long opsObjectId) {
        this.opsObjectId = opsObjectId;
    }

    public String getOpsObjectContent() {
        return opsObjectContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setOpsObjectContent(String opsObjectContent) {
        this.opsObjectContent = opsObjectContent;
    }
}
