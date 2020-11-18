package com.helix.demo.rpc.hessian;

import java.io.Serializable;

public class Bean implements Serializable{
	
	private String name = null;
	private Integer age = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}