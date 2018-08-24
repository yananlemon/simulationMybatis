package com.lemon.test;


import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lemon.dao.IEmployeeDAO;
import com.lemon.entity.Employee;
import com.lemon.session.ISqlSession;
import com.lemon.session.ISqlSessionFactory;
import com.lemon.session.SqlSessionFactoryBuilder;

public class TestMain {

	public static Date convertDate(String date){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public static void main(String[] args) {
		try  
		{  
			InputStream	inputStream =  TestMain.class.getClassLoader().getResourceAsStream("simulation_mybatis_config.xml");  
			ISqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);  
			ISqlSession session = sqlSessionFactory.openSession();  

			IEmployeeDAO dao=session.getMapper(IEmployeeDAO.class);
			
			//测试查询List
			List<Employee> users=dao.listEmployees();
			System.out.println(users);

			//测试查询单个对象
//			Employee employe=dao.getById(1);
//			System.out.println(employe);

			//test insert
			Employee andy=new Employee();
			andy.setBirthday(convertDate("1997-5-27"));
			andy.setFirstName("bill");
			andy.setLastName("gates");
			andy.setName("bill.gates");
			andy.setSex(2);
			System.out.println(dao.insert(andy));
			
			//test update
			/*Employee andy=new Employee();
			andy.setEmployeeId(10);
			andy.setBirthday(convertDate("1968-12-27"));
			andy.setFirstName("bill");
			andy.setLastName("gates");
			andy.setName("bill.gates");
			andy.setSex(2);
			System.out.println(dao.update(andy));*/
			
			/*List<Employee> users=dao.listEmployees();
			System.out.println(users); 
			
			//测试删除
			//System.out.println(dao.delete(10));
			
			List<Employee> users2=dao.listEmployees();
			System.out.println(users2); */
			
			

		}  
		catch (Exception e)  
		{  
			e.printStackTrace();  
		}  
	}


}
