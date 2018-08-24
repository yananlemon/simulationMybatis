package com.lemon.session;

import java.io.InputStream;

import com.lemon.config.Configuration;
import com.lemon.reader.XMLConfigReader;

public class SqlSessionFactoryBuilder {
	public  ISqlSessionFactory build(InputStream input){
		// 加载XMLConfigReader
		try {
			Class.forName(XMLConfigReader.class.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("不能加载XMLConfigReader！");
		}
		return build(Configuration.getInstance()); 
	}
	
	
	public  ISqlSessionFactory build(Configuration config){
		return new SqlSessionFactoryImpl(config); 
	}
	
}
