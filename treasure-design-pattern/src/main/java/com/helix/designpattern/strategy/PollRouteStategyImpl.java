package com.helix.designpattern.strategy;

import java.util.List;

/**
 * url路由轮询
 */
public class PollRouteStategyImpl implements RouteStategy {
    private int index = -1;

    @Override
    public String route(String url, List<String> serviceList) {
        if(index >= serviceList.size()-1){
            index = 0;
        }else{
            index = index + 1;
        }

        return serviceList.get(index);
    }
}
