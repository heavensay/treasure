package com.helix.designpattern.proxy.remoteproxy.plain;

import java.io.IOException;

/**
 * 代理模式(远程代理模式)
 * 以远程获取资讯信息作为场景，仿照jdk rmi模式，自定义实现stub，skeleton。
 *
 * 客户端启动类
 * Client_Stub是stub，里面封装了网络通信细节，序列化，代理实现了接口IInfoRemote；
 * 此例子参考：https://blog.csdn.net/xinghun_4/article/details/45787549 java RMI原理详解
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 19:23
 */
public class Client {
    public static void main(String[] args) throws IOException {
        IInfoRemote infoRemote = new Client_Stub();
        System.out.println("client getinfo:"+infoRemote.getInfo());
    }
}
