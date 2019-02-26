package com.helix.datatrail.entity;

import com.helix.datatrail.annotation.TrailTable;

import java.util.Date;

/**
 * @author lijianyu
 * @date 2018/12/29 16:49
 */
@TrailTable(objectTableName = "user", snapshotTableName = "t_ops_history", objectIdName = "id")
public class User {

    private Long id;

    private String name;

    private Integer age;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
