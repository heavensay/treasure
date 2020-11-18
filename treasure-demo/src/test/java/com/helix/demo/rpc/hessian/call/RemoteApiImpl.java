package com.helix.demo.rpc.hessian.call;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-18 13:40
 */
public class RemoteApiImpl implements RemoteApi {
    @Override
    public String sayHello() {
        return "remote hello!";
    }
}
