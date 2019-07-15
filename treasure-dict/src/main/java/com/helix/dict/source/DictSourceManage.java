package com.helix.dict.source;

import java.util.Collection;

/**
 * @author ljy
 * @date 2019/7/11 20:57
 */
public interface DictSourceManage {

    /**
     * 数据源注册
     * @param dictSource
     */
    void register(IDictSource dictSource);

    /**
     * 移除数据源
     * @param dictSource
     */
    void unregister(IDictSource dictSource);
}
