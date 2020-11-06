package com.helix.designpattern.strategy;

import java.util.List;

/**
 * url路由hash策略
 */
public class HashRouteStategyImpl implements RouteStategy {
    @Override
    public String route(String url, List<String> serviceList) {
        int i = url.hashCode()%serviceList.size();
        return serviceList.get(i);
    }
}
