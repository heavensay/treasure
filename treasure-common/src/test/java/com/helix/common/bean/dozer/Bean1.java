package com.helix.common.bean.dozer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lijianyu
 * @Date: 2018/7/26 16:10
 */
public class Bean1 {
    private Integer id;

    private String content;

    private BigDecimal money;

    private Date time;

    private String userName;

    private Inner1 inner1;

    private List<Inner1> inner1s;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Inner1 getInner1() {
        return inner1;
    }

    public void setInner1(Inner1 inner1) {
        this.inner1 = inner1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Inner1> getInner1s() {
        return inner1s;
    }

    public void setInner1s(List<Inner1> inner1s) {
        this.inner1s = inner1s;
    }
}
