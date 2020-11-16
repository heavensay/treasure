package com.helix.designpattern.proxy.virtualproxy;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 12:44
 */
public class CoverDisplay implements LoaderCallback{

    ImageLoader imageLoader;

    public CoverDisplay(ImageLoader imageLoader){
        this.imageLoader = imageLoader;
        if(imageLoader instanceof ImageLoaderAsyncProxy){
            ((ImageLoaderAsyncProxy) imageLoader).setLoaderCallback(this);
        }
    }

    public void display(){
        System.out.println("封面图片:"+imageLoader.acquirePic());
    }

    @Override
    public void call(String pic) {
        display();
    }
}
