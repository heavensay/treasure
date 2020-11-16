package com.helix.designpattern.proxy.remoteproxy.plain;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 *
 * @author lijianyu@yunloan.net
 * @date 2020-11-15 18:04
 */
public class Server_Skeleton extends Thread{
    private  ServerSocket serverSocket = new ServerSocket(8080);;


    private IInfoRemote infoRemote;
    public Server_Skeleton(IInfoRemote infoRemote) throws IOException {
        this.infoRemote = infoRemote;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            while (true){
                socket = getServerSocket().accept();
                InputStream inputStream = socket.getInputStream();
                byte[] bf = new byte[inputStream.available()];
                inputStream.read(bf);
                String method = new String(bf, "utf-8");
                //可以继续完善，根据客户端传的接口名，方法名，参数，来实际对应到服务端的调用
                if ("getInfo".startsWith(method)) {
                    String info = getInfoRemote().getInfo();
                    socket.getOutputStream().write(info.getBytes("utf-8"));
                } else {
                    socket.getOutputStream().write("not support remote api".getBytes("utf-8"));
                }
                System.out.println("server loop ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IInfoRemote getInfoRemote() {
        return infoRemote;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
