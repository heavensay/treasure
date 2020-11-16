package com.helix.designpattern.proxy.virtualproxy;

/**
 * 图片异步加载完成后，回调
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 12:50
 */
public interface LoaderCallback {
    void call(String pic);
}
