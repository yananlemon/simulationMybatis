package com.lemon.config;

import java.util.HashMap;
import java.util.Map;

import com.lemon.session.SqlMapper;

public class Configuration {
	
	private Map<String,String> connMap = new HashMap<String, String>();
	private Map<String,SqlMapper> sqlMap = new HashMap<String, SqlMapper>();// key:interfaceFullPath+ID
	
	private static Configuration instance = new Configuration();
	
	public static Configuration getInstance(){
		return instance;
	}
	
	private Configuration(){
		this.sqlMap = new HashMap<String, SqlMapper>();
	}
	
	public String toString(){
		return "[sqlMap"+sqlMap+"]";
	}

	
	public Map<String, String> getConnMap() {
		return connMap;
	}

	public void addConnectionInfo(String key,String value){
		this.connMap.put(key, value);
	}



	public Map<String, SqlMapper> getSqlMap() {
		return sqlMap;
	}
	
	public SqlMapper getMapperByKey(String key){
		if(key == null || key.equals(""))
			throw new IllegalArgumentException("键值不能为空！");
		SqlMapper rs = sqlMap.get(key);
		if(rs == null)
			throw new RuntimeException(key+"所对应的Mapper为空！");
		return rs;
	}
	
	public void addMapper(String key,SqlMapper value){
		this.sqlMap.put(key, value);
	}
	
}
