package com.helix.datatrail.interceptor;

/**
 * @author lijianyu
 * @date 2019/1/14 18:06
 */
public enum OpsEventTypeEnum {

    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    UNKNOWN("unknown"),
    ;

    private String code;
    OpsEventTypeEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
