package com.helix.designpattern.proxy.remoteproxy.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 远程服务类，根据java内置rmi来实现
 * 集成UnicastRemoteObject用于生成远程对象
 *
 * 远程对象必须实现java.rmi.server.UniCastRemoteObject类，这样才能保证客户端访问获得远程对象时，
 * 该远程对象将会把自身的一个拷贝以Socket的形式传输给客户端，此时客户端所获得的这个拷贝称为“存根”，
 * 而服务器端本身已存在的远程对象则称之为“骨架”。其实此时的存根是客户端的一个代理，用于与服务器端的通信，
 * 而骨架也可认为是服务器端的一个代理，用于接收客户端的请求之后调用远程方法来响应客户端的请求。
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 17:36
 */
public class ServerInfoRemoteImpl extends UnicastRemoteObject implements IInfoRemote {

    protected ServerInfoRemoteImpl() throws RemoteException {
    }

    @Override
    public String getInfo() {
        return "rmi server info !";
    }
}
