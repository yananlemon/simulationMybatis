package com.lemon.entity;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7960019073363782206L;
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String name;
	private Integer sex;
	private Date birthday;
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName="
				+ firstName + ", lastName=" + lastName + ", name=" + name
				+ ", sex=" + sex + ", birthday=" + birthday + "]\n";
	}
	
	
	
}
