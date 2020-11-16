package com.helix.designpattern.proxy.remoteproxy.plain;

import java.rmi.RemoteException;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 18:04
 */
public class ServerInfoRemoteImpl implements IInfoRemote {
    @Override
    public String getInfo() throws RemoteException {
        return "server info !";
    }
}
