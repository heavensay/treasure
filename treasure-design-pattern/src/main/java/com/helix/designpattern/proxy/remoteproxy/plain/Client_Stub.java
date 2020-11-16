package com.helix.designpattern.proxy.remoteproxy.plain;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * 代理类，实现了IInfoRemote接口，实际由远程服务提供此信息的返回。
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 18:56
 */
public class Client_Stub implements IInfoRemote {
    private Socket socket = new Socket("127.0.0.1", 8080);

    public Client_Stub() throws IOException {
    }

    @Override
    public String getInfo() throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("getInfo".getBytes("utf-8"));

        outputStream.flush();

        byte[] bf = new byte[socket.getInputStream().available()];
        socket.getInputStream().read(bf);
        String info = new String(bf,"utf-8");

        return info;
    }
}
