package com.helix.common.bean.mapstruct;

import java.util.Date;
import java.util.List;

/**
 * @Author: lijianyu
 * @Date: 2018/7/26 16:10
 */
public class Bean2 {
    private Integer id;

    private String content;

    private Integer money;

    private Date time;

    private String user_name;

    private Inner2 inner1;

    private List<Inner2> inner2s;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Inner2 getInner1() {
        return inner1;
    }

    public void setInner1(Inner2 inner2) {
        this.inner1 = inner2;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<Inner2> getInner2s() {
        return inner2s;
    }

    public void setInner2s(List<Inner2> inner2s) {
        this.inner2s = inner2s;
    }
}
