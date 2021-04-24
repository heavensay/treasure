package com.helix.common.test.jmockit.multideep;

import mockit.*;
import org.junit.Test;

/**
 * service->manage->dao多层次注入mock测试
 * @author lijianyu
 * @date 2021/3/11 下午3:01
 */
public class DeepJmockitTest {

    private DeepService deepService ;
//            = new DeepService();

//    @Mocked
    @Tested
    private DeepService mockService;

    @Mocked
    private DeepDao deepDao;

    @Test
    public void echo(){
        System.out.println("111");
        deepService.echo("123");
        System.out.println(deepService.echo("123"));
        System.out.println("222");
    }

    @Test
    public void echo2(){
//        new Expectations() {//录制预期模拟行为
//            {
//                //声明要Mock的方法(注：其它方法按照正常的业务逻辑运行)
//                mockService.echo(anyString);
//                //期望方法返回的结果
//                result = "echo msg";
//            }
//        };
        System.out.println(mockService.echo("123"));
    }

    @Test
    public void echo3(){
        MockUp proxyStub = new MockUp<DeepDao>() {
            // 需要使用@Mock标记,否则jmockit不会处理;
            // 而且方法的签名必须与接口中方法签名一致，否则jmockit会报错
            @Mock
            public String queryContent(){
                return "default";
            }
        };
        System.out.println(mockService.echo("123"));
    }
}
