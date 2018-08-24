package com.lemon.session;

import java.util.Map;

public class Mapper {
	private String interfaceFullPath;
	private Map<String,SqlMapper> sqlMap;
	private String resultType;
	
	public String getInterfaceFullPath() {
		return interfaceFullPath;
	}
	public void setInterfaceFullPath(String interfaceFullPath) {
		this.interfaceFullPath = interfaceFullPath;
	}
	
	
	public Map<String, SqlMapper> getSqlMap() {
		return sqlMap;
	}
	public void setSqlMap(Map<String, SqlMapper> sqlMap) {
		this.sqlMap = sqlMap;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
}
