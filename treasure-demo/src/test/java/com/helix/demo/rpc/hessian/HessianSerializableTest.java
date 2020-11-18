package com.helix.demo.rpc.hessian;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author lijianyu@yunloan.net
 * @date 2020-11-17 10:50
 */
public class HessianSerializableTest{
    Bean bean = new Bean();
    {{
        bean.setAge(18);
        bean.setName("tom");
    }};

    @Test
    public void hserializableInt() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(bos);
        ho.writeInt(3);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        HessianInput hi = new HessianInput(bis);
        Object result = hi.readInt();

        System.out.println("反序列化结果:"+result);
    }

    @Test
    public void hserializableObject() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(bos);
        ho.writeObject(bean);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        HessianInput hi = new HessianInput(bis);
        Object desrialBean = hi.readObject();

        System.out.println("序列化bean:"+bean);
        System.out.println("反序列化bean:"+desrialBean);
    }

}
