package com.helix.designpattern.proxy.virtualproxy;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 12:03
 */
public class ImageLoaderImpl implements ImageLoader {
    @Override
    public String acquirePic(){
        try {
            //模拟获取图片过程是同步、耗时的
            Thread.sleep(2000L);
            return "this is a picture";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "this is not a picture";
    }
}
