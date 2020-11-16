package com.helix.designpattern.proxy.remoteproxy.plain;

import java.io.IOException;

/**
 * 代理模式(远程代理模式)
 * 已远程获取资讯信息作为场景，仿照jdk rmi模式，自定义实现stub，skeleton。
 *
 * 服务端启动类
 * Server_Skeleton是骨架，里面封装了网络通信细节，序列化，调用方法匹配；
 * 此例子参考：https://blog.csdn.net/xinghun_4/article/details/45787549 java RMI原理详解
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 19:26
 */
public class Server {
    public static void main(String[] args) throws IOException {
        Server_Skeleton skeleton = new Server_Skeleton(new ServerInfoRemoteImpl());

        skeleton.start();
    }
}
