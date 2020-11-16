package com.helix.designpattern.proxy.remoteproxy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * rmi远程访问需要扩展Remote接口
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 17:36
 */
public interface IInfoRemote extends Remote {
    String getInfo() throws RemoteException;
}
