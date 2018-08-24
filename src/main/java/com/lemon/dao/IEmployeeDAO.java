package com.lemon.dao;

import java.util.List;

import com.lemon.entity.Employee;

public interface IEmployeeDAO {
	public List<Employee> listEmployees();
	
	public Employee getById(int id);
	
	public int insert(Employee obj);
	
	public int update(Employee obj);
	
	public int delete(int id);
	
}
