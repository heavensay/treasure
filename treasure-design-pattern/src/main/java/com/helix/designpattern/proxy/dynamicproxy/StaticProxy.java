package com.helix.designpattern.proxy.dynamicproxy;

/**
 * 代理类：代理商
 * @author lijianyu@yunloan.net
 * @date 2020-11-16 15:35
 */
public class StaticProxy implements Sell {

    private Sell target;

    public StaticProxy(Sell agentSell){
        this.target = agentSell;
    }

    @Override
    public void sell() {
        System.out.println("StaticAgentSell discount 20%");
        target.sell();
        System.out.println("StaticAgentSell add point 300");
    }
}
