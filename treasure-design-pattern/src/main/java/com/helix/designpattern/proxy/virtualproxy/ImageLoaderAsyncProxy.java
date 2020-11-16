package com.helix.designpattern.proxy.virtualproxy;

/**
 * 功能：代理类，控制ImageLoaderImpl下载图过程，首先返回默认图片，在ImageLoaderImpl下载图片完成后，在回调真正图片内容
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 12:40
 */
public class ImageLoaderAsyncProxy implements ImageLoader{

    private ImageLoader imageLoader;

    private LoaderCallback loaderCallback;

    String DEFAULT_PIC = "default pic";

    private String pic = DEFAULT_PIC;

    public ImageLoaderAsyncProxy(ImageLoader imageLoader){
        this.imageLoader = imageLoader;
    }

    @Override
    public String acquirePic() {
        if(pic.equals(DEFAULT_PIC)){
            //异步执行，获取图片是费时的
            new Thread(() -> {
                pic = imageLoader.acquirePic();
                loaderCallback.call(pic);
            }).start();
        }
        return pic;
    }

    public void setLoaderCallback(LoaderCallback loaderCallback){
        this.loaderCallback = loaderCallback;
    }
}
