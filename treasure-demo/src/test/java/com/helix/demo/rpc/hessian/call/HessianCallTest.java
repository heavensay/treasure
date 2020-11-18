package com.helix.demo.rpc.hessian.call;

import com.caucho.hessian.client.HessianProxyFactory;
import org.junit.Test;

import java.net.MalformedURLException;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-18 13:23
 */
public class HessianCallTest {

    /**
     * hessian java jar包自带的是通过http请求来调用远程过程；但是本省hessian是二进制扩语言的，只要服务端实现此协议我们也可以再tcp层进行解析调用
     * 原理：说白了，就是通过http post请求，body是二进制数据传输，服务端按hessian反序列化，再按照hessian rpc调用协议来解析，执行远程方法，返回结果
     * @throws MalformedURLException
     */
    @Test
    public void rpcCall() throws MalformedURLException {
        String url = "http://hessian.caucho.com/test/test";

        HessianProxyFactory factory = new HessianProxyFactory();
        RemoteApi remoteApi = (RemoteApi) factory.create(RemoteApi.class, url);

        System.out.println("hello(): " + remoteApi.sayHello());
    }
}
