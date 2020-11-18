package com.helix.demo.java.lang.io.serializable;

import com.helix.demo.java.lang.io.serializable.util.ObjectsTranscoder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestSerializable{
	
	private static Logger logger = LoggerFactory.getLogger(TestSerializable.class);

	/**
	 * 基本的序列化和反序列化
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void serializableTest() throws IOException, ClassNotFoundException {
		Bean bean = new Bean();
		bean.setAge(18);
		bean.setName("tom");
		System.out.println("源bean:"+bean);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(bean);

		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);

		System.out.println("经过序列化和反序列后bean："+ois.readObject());
	}

	/**
	 * 序列化到文件中，再从文件中反序列化回来
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void serializable2File() throws IOException, ClassNotFoundException{
		Bird bird = new Bird(1,"first bird");
		
		System.out.println(bird);
		Map map = new HashMap();
//		File file = new File("bird.ser");
		File file = new File("map.ser");
		if(!file.exists()){
			//file.mkdir();
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
//		oos.writeObject(bird);
		oos.writeObject(map);
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		Object o = ois.readObject();
		System.out.println(o);
		System.out.println(file.getAbsolutePath());
		ois.close();
		fis.close();
	}

	/**
	 * ObjectsTranscoder工具测试
	 */
	@Test
	public void test3Object(){
		ObjectsTranscoder<Bean> otc = new ObjectsTranscoder<Bean>();
		Bean bean1 = new Bean();
		bean1.setAge(18);
		bean1.setName("tom");
		
		byte[] bean1Bytes = otc.serialize(bean1);
		
		Bean bean_1 = otc.deserialize(bean1Bytes);
		System.out.println(bean1);
		logger.debug("before serialize bean:"+bean1+"\n" +
					 "after serialize bean:"+bean_1+" age="+bean_1.getAge()+" name="+bean_1.getName());
	}

	@Test
	public void serialList(){
		ObjectsTranscoder<ArrayList> otc = new ObjectsTranscoder<ArrayList>();
		ArrayList list = new ArrayList();
		Bean bean1 = new Bean();
		bean1.setAge(18);
		bean1.setName("tom");

		Bean bean2 = new Bean();
		bean2.setAge(19);
		bean2.setName("tom2");
		
		list.add(bean1);
		list.add(bean2);
		
		byte[] bean1Bytes = otc.serialize(list);
		
		ArrayList<Bean> list_1 = otc.deserialize(bean1Bytes);
		
		for (Bean item : list_1) {
			logger.debug("before serialize bean:"+item+"\n" +
					 "after serialize bean:"+item+" age="+item.getAge()+" name="+item.getName());
			
		}
	}	
}
