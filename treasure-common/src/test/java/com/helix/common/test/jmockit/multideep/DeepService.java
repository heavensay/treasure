package com.helix.common.test.jmockit.multideep;

/**
 * @author lijianyu
 * @date 2021/3/11 下午2:57
 */
public class DeepService {

    private DeepManage deepManage = new DeepManage();

    public String echo(String msg){
        System.out.println("DeepService echo:"+msg);

        return deepManage.getEchoMsg(msg);
    }
}
