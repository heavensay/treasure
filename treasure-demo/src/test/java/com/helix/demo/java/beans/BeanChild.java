package com.helix.demo.java.beans;

/**
 * @author ljy
 * @date 2019/7/4 15:00
 */
public class BeanChild extends BeanParent {
    private int age;
    private String name;
    private boolean isLady;

    private String title;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLady() {
        return isLady;
    }

    public void setLady(boolean lady) {
        isLady = lady;
    }

    /*方法命名不符合bean规范，会获取不到title属性*/
    public String queryTitle(){
        return title;
    }

    public String getLabel(){
      return "label";
    }
}
