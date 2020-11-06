package com.helix.designpattern.strategy;

import java.util.List;

public interface RouteStategy {

    String route(String url, List<String> serviceList);

}
