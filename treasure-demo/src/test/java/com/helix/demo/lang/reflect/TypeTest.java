package com.helix.demo.lang.reflect;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ljy
 * @date 2019/6/12 20:47
 */
public class TypeTest {

    /**
     * 从中可以观看各种泛型信息
     */
    @Test
    public void typeDetail(){
        Arrays.stream(B.class.getDeclaredFields()).forEach( e ->{
            System.out.println(e.getName());
            Type type = e.getGenericType();
            cycle(type);
        });
    }

    private void cycle(Type...types){
        Arrays.stream(types).forEach(type->{
            print(type,"====");
            if(type instanceof TypeVariable){
                Type[] bounds = ((TypeVariable) type).getBounds();
                System.out.println("genericdeclaration:"+((TypeVariable) type).getGenericDeclaration());
                cycle(bounds);

            }else if(type instanceof ParameterizedType){
                Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();//Map<K,V>这种就会有多个type

                print(((ParameterizedType) type).getOwnerType(),"ParameterizedType ownertype:");
                print(((ParameterizedType) type).getRawType(),"ParameterizedType getRawType:");

                cycle(actualTypes);
            }else if(type instanceof WildcardType){
                Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
                Type[] upperBounds = ((WildcardType) type).getUpperBounds();

                print(lowerBounds,"WildcardType");
                print(upperBounds,"WildcardType");
            }else if(type instanceof GenericArrayType){
                cycle(((GenericArrayType) type).getGenericComponentType());
            }else if(type instanceof Class){
                System.out.println("classname:"+((Class) type).getName());
                return;
            }else{
                System.out.println("error!!!!");
            }
        });
    }

    private void print(Type type,String label){
        if (type == null) return;
        System.out.println(label);
        System.out.println("type name:"+type.getTypeName());
        System.out.println("type class name:"+type.getClass().getName());
    }

    private void print(Type[] types,String label){
        Arrays.stream(types).forEach(item->{
            System.out.println(label);
            System.out.println("type name:"+item.getTypeName());
            System.out.println("type class name:"+item.getClass().getName());
        });
    }

    @Test
    public void className(){
        String str = new String("ddd");
        System.out.println(str.getClass().getName());
        System.out.println(String.class.getName());
        System.out.println(String.class.getClass().getName());
    }

    /**
     * class泛型内容
     * @throws Exception
     */
    @Test
    public void classGenericType() throws Exception{
//        A<String> a = new A<String>(new String("ddd"));//<String>在运行时会擦除，保留不下来
        Class clazz = A.class;
//        Class clazz = String.class;

        System.out.println("getGenericSuperclass:"+StringUtils.join(clazz.getGenericSuperclass(),","));
        System.out.println("getGenericInterfaces:"+StringUtils.join(clazz.getGenericInterfaces(),","));
        //class是数组时，返回数组中元素的的class类型；否则返回null
        System.out.println("getComponentType:"+StringUtils.join(clazz.getComponentType(),","));
        System.out.println("getTypeName:"+StringUtils.join(clazz.getTypeName(),","));
        //获取class的泛型描述信息
        System.out.println("getTypeParameters:"+StringUtils.join(clazz.getTypeParameters(),","));

        //获取泛型的上边界；边界没明确设置，则返回Object
        Type bType = ((TypeVariable)clazz.getTypeParameters()[0]).getBounds()[0];
        System.out.println("getBounds:"+bType);
    }

    /**
     * 获取方法参数中的泛型类型
     * 如果参数中类型是嵌套行泛型，还需要判断
     * @throws Exception
     */
    @Test
    public void methodGenericTypeInfo() throws Exception{
        A a = new A<String>(new String("ddd"));
        Method method = a.getClass().getMethod("getList",List.class);
        System.out.println(method.getReturnType());
        System.out.println(method.getGenericReturnType());
        System.out.println("getGenericParameterTypes:"+StringUtils.join(method.getGenericParameterTypes()));
        System.out.println("getParameterTypes:"+StringUtils.join(method.getParameterTypes()));

        Type type = ((ParameterizedType)method.getParameters()[0].getParameterizedType()).getActualTypeArguments()[0];
        System.out.println(type);
    }

    /**
     * 获取方法参数中的泛型类型
     * 如果参数中类型是嵌套行泛型，还需要判断
     * @throws Exception
     */
    @Test
    public void methodGenericType() throws Exception{
        A a = new A<String>(new String("ddd"));
        Method method = a.getClass().getMethod("getList",List.class);
        System.out.println(method.getReturnType());
        System.out.println(method.getGenericReturnType());
        Type type = ((ParameterizedType)method.getParameters()[0].getParameterizedType()).getActualTypeArguments()[0];
        System.out.println(type);
        Assert.assertSame(String.class,type);

        System.out.println("========");

        Method method2 = a.getClass().getMethod("getSource", Object.class);
        Type type2 = ((TypeVariable)method2.getParameters()[0].getParameterizedType()).getBounds()[0];
        System.out.println(method2.getReturnType());
        System.out.println(method2.getGenericReturnType());
        System.out.println(type2);
        Assert.assertSame(Object.class,type2);
    }
}