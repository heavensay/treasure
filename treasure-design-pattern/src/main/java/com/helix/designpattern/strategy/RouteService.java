package com.helix.designpattern.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy Pattern策略模式联系
 * 场景：在web访问中，会针对url请求进行路由，路由算法会有多种实现，采用不同算法，就会选择不同的服务器
 * 关键点：变化的策略提取出来，各种子类实现各自策略算法；主类Has a 各种策略，就实现了策略模式
 */
public class RouteService {

    RouteStategy routeStategy = new HashRouteStategyImpl();
    List<String> serviceList = new ArrayList<>();
    {{
        serviceList.add("service1");
        serviceList.add("service2");
        serviceList.add("service3");
    }}

    public String requestRoute(String url){
        String serviceName = routeStategy.route(url,serviceList);
        return serviceName;
    }

    /**
     * 设置路由策略
     * @param routeStategy
     */
    public void setRouteStategy(RouteStategy routeStategy){
        this.routeStategy = routeStategy;
    }

    public static void main(String[] args){
        String url1 = "req1";
        String url2 = "req2";
        String url3 = "req3";
        String url4 = "req4";
        String url5 = "req5";
        String url6 = "req1";

        System.out.println("hash路由策略6个请求返回的服务器列表");
        RouteService service = new RouteService();
        System.out.println(service.requestRoute(url1));
        System.out.println(service.requestRoute(url2));
        System.out.println(service.requestRoute(url3));
        System.out.println(service.requestRoute(url4));
        System.out.println(service.requestRoute(url5));
        System.out.println(service.requestRoute(url6));

        System.out.println("轮询路由策略6个请求返回的服务器列表");
        //改变默认路由策略
        service.setRouteStategy(new PollRouteStategyImpl());
        System.out.println(service.requestRoute(url1));
        System.out.println(service.requestRoute(url2));
        System.out.println(service.requestRoute(url3));
        System.out.println(service.requestRoute(url4));
        System.out.println(service.requestRoute(url5));
        System.out.println(service.requestRoute(url6));
    }

}
