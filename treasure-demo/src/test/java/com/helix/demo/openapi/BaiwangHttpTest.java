package com.helix.demo.openapi;

import org.junit.Test;

/**
 * @author ljy
 * @date 2019/7/16 11:21
 */
public class BaiwangHttpTest {
    @Test
    public void assetVerify(){
        AssetVerifyParam param = new AssetVerifyParam();
        param.setOrgNo("467ea0e4a964afc63d1e");
        param.setInvoiceCode("033001800104");
        param.setInvoiceNumber("59786989");
        param.setBillingDate("2019-07-01");
        param.setCheckCode("347208");
        param.setTotalAmount("1383.5");
        BaiwangHttp.assetVerfiy(param);
    }

}
