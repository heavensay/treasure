package com.helix.datatrail.entity;

import java.util.Date;

/**
 * @author lijianyu
 * @date 2019/1/2 14:33
 */
public class DataTrailEntity {

    private Long id;

    private Long opsSearchId;

    private Date opsTime;

    private String opsOperationIdentity;

    private String opsObjectName;

    private Long opsObjectId;

    private String opsObjectContent;

    private String opsEvent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOpsSearchId() {
        return opsSearchId;
    }

    public void setOpsSearchId(Long opsSearchId) {
        this.opsSearchId = opsSearchId;
    }

    public Date getOpsTime() {
        return opsTime;
    }

    public void setOpsTime(Date opsTime) {
        this.opsTime = opsTime;
    }

    public String getOpsOperationIdentity() {
        return opsOperationIdentity;
    }

    public void setOpsOperationIdentity(String opsOperationIdentity) {
        this.opsOperationIdentity = opsOperationIdentity;
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

    public void setOpsObjectContent(String opsObjectContent) {
        this.opsObjectContent = opsObjectContent;
    }

    public String getOpsEvent() {
        return opsEvent;
    }

    public void setOpsEvent(String opsEvent) {
        this.opsEvent = opsEvent;
    }
}
