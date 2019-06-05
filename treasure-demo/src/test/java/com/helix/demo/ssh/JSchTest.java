package com.helix.demo.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * jsch可以作为ssh的客户端，来连接ssh服务器；可以进行端口转发功能，突破防火墙限制；
 * @author ljy
 * @date 2019/6/4 9:28
 */
public class JSchTest {

    @Test
    public void initSSHConnect() throws Exception {
        System.out.println("init ssh env====");
        JSch jsch = new JSch();
        Session session = jsch.getSession("root", "19.19.19.122", 22);
        session.setPassword("123456");
//        session.setPassword("xxxx");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息

        //ssh -L 192.168.0.102:5555:192.168.0.101:3306 yunshouhu@192.168.0.102  正向代理
        int assinged_port = session.setPortForwardingL("127.0.0.1",2200, "19.19.19.122", 22);//端口映射 转发
        System.out.println("localhost:" + assinged_port);
    }

    /**
     * 执行远程linux上ls命令
     * @throws Exception
     */
    @Test
    public void execLs() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession("root", "19.19.19.122", 22);
        session.setPassword("123456");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
        //正向代理端口映射 转发
        int assinged_port = session.setPortForwardingL("127.0.0.1",2200, "19.19.19.122", 22);
        System.out.println("localhost:" + assinged_port);

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand("ls");
        channel.connect();

        InputStream is = channel.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String str = null;
        while ((str = br.readLine()) != null){
            System.out.println(str);
        }

        //关闭流
        br.close();
        //关闭通道
        session.disconnect();
        System.out.println(channel.isClosed());
    }

    /**
     * 使用ssh端口转发功能，绕过端口限制，访问mysql数据库
     * 假设本地A的IP：19.19.19.193，SSH服务器B的IP:19.19.19.122，数据库服务器C的IP:19.19.19.194；
     * A->C限制，B->C可以，A->B可以；那么我们可以通过A->B(ssh 转发)->C，来间接实现A->B数据库的访问
     */
    @Test
    public void forwardDBConnect()throws Exception{
        String localA = "19.19.19.193";
        String sshB = "19.19.19.122";
        String dbC = "19.19.19.193";//测试环境本地跟数据库为同一台

        JSch jsch = new JSch();
        Session session = jsch.getSession("root", sshB, 22);
        session.setPassword("123456");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
        //正向代理端口映射 转发
        int assinged_port = session.setPortForwardingL(localA,5555, dbC, 3306);


        // 注册 JDBC 驱动
        //注意数据库连接url中的ip需要跟上面ssh中配置的bing_address保持一致
        Connection conn = DriverManager.getConnection("jdbc:mysql://"+localA+":5555/diy?serverTimezone=UTC","root","123456");

        PreparedStatement preparedStatement = conn.prepareStatement("select * from user limit 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getLong("id"));
            System.out.println(resultSet.getString("user_name"));
        }
        resultSet.close();
        conn.close();
    }
}
