package com.helix.demo.lang;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Bigdecimal基本测试
 * @author ljy
 * @date 2019/5/31 20:31
 */
public class BigDecimalTest {

    /**
     * 获取小数内容
     */
    @Test
    public void queryRemebine(){
        BigDecimal b = new BigDecimal("1.23456");
        BigDecimal remainder = b.remainder(BigDecimal.ONE );
        System.out.println(remainder);

        System.out.println(new BigDecimal("1").remainder(BigDecimal.ONE ));
    }

    @Test
    public void format(){
        BigDecimal b = new BigDecimal("1234.23456");
        b.setScale(0, RoundingMode.HALF_UP);//四舍五入
        DecimalFormat format = null;
        format = new DecimalFormat(",###");//整数每三位用","隔开；
        System.out.println(format.format(b));

        format = new DecimalFormat(",#00");//整数每三位用","隔开；0表示位数不够使用0来填充
        System.out.println(format.format(b));

    }
}
