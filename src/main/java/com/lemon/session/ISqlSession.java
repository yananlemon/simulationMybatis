package com.lemon.session;

import java.sql.SQLException;
import java.util.List;

public interface ISqlSession {
	/**
	 * Retrieve a single row mapped from the statement key
	 * @param <T> the returned object type
	 * @param statement
	 * @return Mapped object
	 */
	<T> T selectOne(String statement) throws SQLException ;

	/**
	 * Retrieve a single row mapped from the statement key and parameter.
	 * @param <T> the returned object type
	 * @param statement Unique identifier matching the statement to use.
	 * @param parameter A parameter object to pass to the statement.
	 * @return Mapped object
	 */
	<T> T selectOne(String statement, Object parameter)throws SQLException ;

	/*<T> T selectOne(String statement, Object parameter,boolean flag)throws SQLException ;*/


	/**
	 * Retrieve a list of mapped objects from the statement key and parameter.
	 * @param <E> the returned list element type
	 * @param statement Unique identifier matching the statement to use.
	 * @return List of mapped object
	 */
	<E> List<E> selectList(String statement)throws SQLException ;

	/**
	 * Retrieve a list of mapped objects from the statement key and parameter.
	 * @param <E> the returned list element type
	 * @param statement Unique identifier matching the statement to use.
	 * @param parameter A parameter object to pass to the statement.
	 * @return List of mapped object
	 */
	<E> List<E> selectList(String statement, Object parameter)throws SQLException ;



	/**
	 * 根据给定的Mapper接口获取该接口的代理类
	 * @param mapperInterfaceClass
	 * @return mapperInterfaceClass的代理类
	 */
	<T>  T getMapper(Class<?> mapperInterfaceClass);

	/**
	 * Execute an insert statement.
	 * @param statement Unique identifier matching the statement to execute.
	 * @return int The number of rows affected by the insert.
	 */
	int insert(String statement)throws SQLException;

	/**
	 * Execute an insert statement with the given parameter object. Any generated
	 * autoincrement values or selectKey entries will modify the given parameter
	 * object properties. Only the number of rows affected will be returned.
	 * @param statement Unique identifier matching the statement to execute.
	 * @param parameter A parameter object to pass to the statement.
	 * @return int The number of rows affected by the insert.
	 */
	int insert(String statement, Object parameter,String parameterType)throws SQLException;
	
	/**
	 * Execute an insert statement with the given parameter object. Any generated
	 * autoincrement values or selectKey entries will modify the given parameter
	 * object properties. Only the number of rows affected will be returned.
	 * @param statement Unique identifier matching the statement to execute.
	 * @param parameter A parameter object to pass to the statement.
	 * @return int The number of rows affected by the insert.
	 */
	int update(String statement, Object parameter,String parameterType)throws SQLException;
	
	int delete(String statement, Object parameter)throws SQLException;
}
