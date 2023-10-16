package com.helix.common.bean.mapstruct;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: lijianyu
 * @Date: 2018/7/26 16:10
 */
@Data
public class Bean2<F> {
    private Integer id;

    private String content;

    private Integer money;

    private Date time;

    private String user_name;

    private Inner2 inner1;

    private List<Inner2> inner2s;

    private List<Long> toUids;

}
