package com.helix.designpattern.proxy.virtualproxy;

/**
 * 代理模式：虚拟代理
 * 场景设计：客户端要下载附件，下载是费时的，下载的时候显示默认等待中...
 *
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 11:58
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ImageLoader imageLoader = new ImageLoaderAsyncProxy(new ImageLoaderImpl());
        CoverDisplay coverDisplay = new CoverDisplay(imageLoader);

        coverDisplay.display();

        //单元测试主线程跑完之后，进程直接退出；我们延长主线程退出时间，让ImageLoaderImpl图片下载完成
        Thread.sleep(5000L);
    }
}
