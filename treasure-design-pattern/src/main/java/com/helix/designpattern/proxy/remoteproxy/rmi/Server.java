package com.helix.designpattern.proxy.remoteproxy.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 代理模式，远程代理
 *注册远程对象,向客户端提供远程对象服务
 *远程对象是在远程服务上创建的，你无法确切地知道远程服务器上的对象的名称
 *但是，将远程对象注册到RMI Service之后，客户端就可以通过RMI Service请求
 *
 * 此例子参考：https://blog.csdn.net/xinghun_4/article/details/45787549 java RMI原理详解
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 21:44
 */
public class Server {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        /* 生成stub和skeleton,并返回stub代理引用 */
        /* 本地创建并启动RMI Service，被创建的Registry服务将在指定的端口上侦听到来的请求
         * 实际上，RMI Service本身也是一个RMI应用，我们也可以从远端获取Registry:
         *     public interface Registry extends Remote;
         *     public static Registry getRegistry(String host, int port) throws RemoteException;
         */
        //RMI服务端注册，开启服务
        IInfoRemote service=new ServerInfoRemoteImpl();
        //绑定到rmi registry中，客户端会从中找到我们注册的InfoRemote服务
        LocateRegistry.createRegistry(6600);
        Naming.rebind("rmi://127.0.0.1:6600/InfoRemote", service);
    }
}
