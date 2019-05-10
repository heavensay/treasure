package com.helix.common;

import java.util.regex.Pattern;

/**
 * 常用正则工具类
 */
public class RegexUtils {

    /*邮箱*/
    public static String EMAIL_REG = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
    /*身份证*/
    public static String ID_CARD_REG = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
    /*手机*/
    public static String MOBILE_REG = "(\\+\\d+)?1[34578]\\d{9}$";
    /*座机电话*/
    public static String PHONE_REG = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
    /*整数（正整数和负整数）*/
    public static String DIGIT_REG = "\\-?[1-9]\\d+";
    /*中文*/
    public static String CHINESE_REG = "^[\u4E00-\u9FA5]+$";
    /*URL*/
    public static String URL_REG = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
    /*中国邮编*/
    public static String POST_CODE_REG = "[1-9]\\d{5}";
    /*IP*/
    public static String IP_REG = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
    /*组织机构代码*/
    public static String ORGANIZATION_CODE_REG = "^[A-Z0-9]{8}-[A-Z0-9]$";
    /*统一社会信用代码 : Uniform social credit identify*/
    public static String USCI_REG = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$";


    /**
     * 验证Email
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkEmail(String email) { 
        return Pattern.matches(EMAIL_REG, email);
    } 
     
    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkIdCard(String idCard) { 
        return Pattern.matches(ID_CARD_REG,idCard);
    } 
     
    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkMobile(String mobile) { 
        return Pattern.matches(MOBILE_REG,mobile);
    } 
     
    /**
     * 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。</p>
     * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkPhone(String phone) { 
        return Pattern.matches(PHONE_REG, phone);
    } 
     
    /**
     * 验证整数（正整数和负整数）
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkDigit(String digit) { 
        return Pattern.matches(DIGIT_REG,digit);
    } 
     

    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkChinese(String chinese) { 
        return Pattern.matches(CHINESE_REG,chinese);
    } 
     
    /**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkURL(String url) { 
        return Pattern.matches(URL_REG, url);
    } 
    
    /**
     * 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkPostcode(String postcode) { 
        return Pattern.matches(POST_CODE_REG, postcode);
    } 
     
    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */ 
    public static boolean checkIpAddress(String ipAddress) { 
        return Pattern.matches(IP_REG, ipAddress);
    }

    /**
     * 匹配组织机构代码(简单匹配，格式，如：MA28W5W7-7)
     * @param organization 组织机构代码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkOrganizationCode(String organization) {
        return Pattern.matches(ORGANIZATION_CODE_REG, organization);
    }

    /**
     * 匹配统一社会信用码(e.g. 91330108MA28W5W77R)
     * @param usci Uniform social credit identify统一社会信用码
     * @return
     */
    public static boolean checkUSCI(String usci) {
        return Pattern.matches(USCI_REG, usci);
    }
}