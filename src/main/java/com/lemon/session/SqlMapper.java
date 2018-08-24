package com.lemon.session;

public class SqlMapper {
	private String interfaceFullPath;
	private String id;
	private String sql;
	private String resultType;
	private String operatorType;
	private String parameterType;
	
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public String getInterfaceFullPath() {
		return interfaceFullPath;
	}
	public void setInterfaceFullPath(String interfaceFullPath) {
		this.interfaceFullPath = interfaceFullPath;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	@Override
	public String toString() {
		return "SqlMapper [interfaceFullPath=" + interfaceFullPath + ", id="
				+ id + ", sql=" + sql + ", resultType=" + resultType
				+ ", operatorType=" + operatorType + ", parameterType="
				+ parameterType + "]";
	}
}
