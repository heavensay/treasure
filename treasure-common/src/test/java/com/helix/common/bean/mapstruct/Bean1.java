package com.helix.common.bean.mapstruct;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lijianyu
 * @Date: 2018/7/26 16:10
 */
@Data
public class Bean1<F> {
    private Integer id;

    private String content;

    private BigDecimal money;

    private Date time;

    private String userName;

    private Inner1 inner1;

    private List<Inner1> inner1s;

    private List<Long> toUids;
}
