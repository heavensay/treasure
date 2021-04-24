package com.helix.common.test.jmockit.multideep;

/**
 * @author lijianyu
 * @date 2021/3/11 下午2:57
 */
public class DeepManage {

    private DeepDao deepDao;

    public String getEchoMsg(String msg){
        System.out.println("DeepManage echo:"+msg);
        return deepDao.queryContent();
    }

    public void setDeepDao(DeepDao deepDao) {
        this.deepDao = deepDao;
    }
}
