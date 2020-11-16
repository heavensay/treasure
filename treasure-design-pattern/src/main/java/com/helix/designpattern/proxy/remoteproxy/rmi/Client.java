package com.helix.designpattern.proxy.remoteproxy.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 远程服务类，根据java内置rmi来实现
 *
 * 此例子参考：https://blog.csdn.net/xinghun_4/article/details/45787549 java RMI原理详解
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 17:42
 */
public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        //获取远程服务的stub，也即远程代理类
        IInfoRemote service=(IInfoRemote) Naming.lookup("rmi://127.0.0.1:6600/InfoRemote");
        String s=service.getInfo();
        System.out.println("客户端获取远程资讯信息:"+s);
    }

}
