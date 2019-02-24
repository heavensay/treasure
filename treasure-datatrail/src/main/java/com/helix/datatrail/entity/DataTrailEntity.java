package com.helix.datatrail.entity;

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

    private String opsSearchObjectName;

    private Long opsSearchObjectId;

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

    public void setOpsObjectContent(String opsObjectContent) {
        this.opsObjectContent = opsObjectContent;
    }

    public String getOpsSearchObjectName() {
        return opsSearchObjectName;
    }

    public void setOpsSearchObjectName(String opsSearchObjectName) {
        this.opsSearchObjectName = opsSearchObjectName;
    }

    public Long getOpsSearchObjectId() {
        return opsSearchObjectId;
    }

    public void setOpsSearchObjectId(Long opsSearchObjectId) {
        this.opsSearchObjectId = opsSearchObjectId;
    }
}
