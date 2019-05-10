package com.helix.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ljy
 * @date 2019/5/9 20:34
 */
public class RegexUtilsTest {

    /**
     * 验证邮箱
     */
    @Test
    public void testCheckEmail() {
        boolean result = RegexUtils.checkEmail("dsds@163.com");
        Assert.assertTrue(result);
    }

    /**
     * 验证身份证号码
     */
    @Test
    public void testCheckIdCard() {
        boolean result = RegexUtils.checkIdCard("432403193902273273");
        Assert.assertTrue(result);
    }

    /**
     * 验证手机号码
     */
    @Test
    public void testCheckMobile() {
        boolean result = RegexUtils.checkMobile("+8613620285733");
        Assert.assertTrue(result);
    }

    /**
     * 验证电话号码
     */
    @Test
    public void testCheckPhone() {
        boolean result = RegexUtils.checkPhone("+860738-4630706");
        Assert.assertTrue(result);
    }

    /**
     * 验证整数（正整数和负整数）
     */
    @Test
    public void testCheckDigit() {
        boolean result = RegexUtils.checkDigit("123132");
        Assert.assertTrue(result);
    }

    /**
     * 匹配中文
     */
    @Test
    public void testCheckChinese() {
        boolean result = RegexUtils.checkChinese("中文");
        Assert.assertTrue(result);
    }

    /**
     * 验证中国邮政编码
     */
    @Test
    public void testCheckPostcode() {
        boolean result = RegexUtils.checkPostcode("417100");
        Assert.assertTrue(result);
    }

    /**
     * 验证URL地址
     */
    @Test
    public  void testCheckURL() {
        boolean result = RegexUtils.checkURL("http://blog.csdn.com:80/xyang81/article/details?name=&abc=中文");
        Assert.assertTrue(result);
    }

    /**
     * 验证IP地址
     */
    @Test
    public void testCheckIpAddress() {
        boolean result = RegexUtils.checkIpAddress("192.1.22.255");
        Assert.assertTrue(result);
    }

    /**
     * 验证组织机构代码
     */
    @Test
    public void checkOrganizationCode() {
        boolean result = RegexUtils.checkOrganizationCode("MA28W5W7-7");
        Assert.assertTrue(result);
    }

    /**
     * 统一社会信用码
     */
    @Test
    public void checkUSCI(){
        Assert.assertTrue(RegexUtils.checkUSCI("91330108MA28W5W77R"));
    }
}