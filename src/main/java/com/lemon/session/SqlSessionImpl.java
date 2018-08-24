package com.lemon.session;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbcp.BasicDataSource;

import com.lemon.config.Configuration;

public class SqlSessionImpl implements ISqlSession {

	private BasicDataSource ds;
	private Configuration config;
	public SqlSessionImpl(Configuration config){
		this.config=config;
		ds=new BasicDataSource();
		ds.setDriverClassName(config.getConnMap().get("driver"));
		ds.setUrl(config.getConnMap().get("url"));
		ds.setUsername(config.getConnMap().get("username"));
		ds.setPassword(config.getConnMap().get("password"));
	}

	@Override
	public <T> T selectOne(String statement) throws SQLException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectOne(String statement, Object parameter) throws SQLException {
		return (T) selectList(statement, parameter).get(0);
	}

	@Override
	public <E> List<E> selectList(String statement) throws SQLException {
		try {
			Connection conn=ds.getConnection();
			PreparedStatement ps=conn.prepareStatement(statement);
			ResultSet rs=ps.executeQuery();
			Set<String> sets=config.getSqlMap().keySet();
			Iterator<String> it=sets.iterator();
			String resultType=null;
			while(it.hasNext()){
				SqlMapper mapper=config.getSqlMap().get(it.next().toString());
				if(mapper.getSql().equals(statement)){
					resultType=mapper.getResultType();
					break;
				}
			}
			return executeQuery(rs, resultType);
		} catch (SQLException e) {
			throw new SQLException("selectOne()");
		}finally{

		}
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) throws SQLException {
		Object[] parameters=(Object[]) parameter;
		try {
			Connection conn=ds.getConnection();
			PreparedStatement ps=conn.prepareStatement(statement);
			for(int i=0;i<parameters.length;i++){
				ps.setObject(i+1, parameters[i]);
			}
			ResultSet rs=ps.executeQuery();
			Set<String> sets=config.getSqlMap().keySet();
			Iterator<String> it=sets.iterator();
			String resultType=null;
			while(it.hasNext()){
				SqlMapper mapper=config.getSqlMap().get(it.next().toString());
				if(mapper.getSql().equals(statement)){
					resultType=mapper.getResultType();
					break;
				}
			}
			return executeQuery(rs, resultType);
		} catch (SQLException e) {
			throw new SQLException("selectOne()");
		}finally{

		}
	}

	/**
	 * 
	 * 鎵ц鏌ヨ鎿嶄綔锛屽苟灏嗘煡璇㈠埌鐨勭粨鏋滀笌閰嶇疆涓殑ResultType鏍规嵁鍙橀噺鍚嶄竴涓�瀵瑰簲锛岄�氳繃鍙嶅皠璋冪敤Set鏂规硶娉ㄥ叆鍚勪釜鍙橀噺鐨勫��
	 * 
	 * @param rs
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> executeQuery(ResultSet rs, String type)
	{

		List<T> list = new ArrayList<T>();
		try{
			while (rs.next())
			{
				Class<?> modelClazz = Class.forName(type);
				Object obj = modelClazz.newInstance();
				for (Method setMethods : modelClazz.getMethods()){
					String fieldName=setMethods.getName().substring(3, setMethods.getName().length());
					fieldName=Character.toLowerCase(fieldName.charAt(0)) +(fieldName.length() > 1 ? fieldName.substring(1) : "");

					if (setMethods.getName().equalsIgnoreCase("set" + fieldName)){
						setMethods.invoke(obj, rs.getObject(fieldName));
					}
				}
				list.add((T) obj);
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}


		return list;
	}

	/**
	 * 根据给定的Mapper接口获取该接口的代理类
	 * @param mapperInterfaceClass
	 * @return mapperInterfaceClass的代理类
	 */
	@Override
	public <T> T getMapper(Class<?> mapperInterfaceClass) {
		@SuppressWarnings("unchecked")
		T clazzImpl =
		(T) Proxy.newProxyInstance(this.getClass().getClassLoader(), 
				new Class[] {mapperInterfaceClass}, new DataBaseOperationProxy(this));

		return clazzImpl;
	}

	@Override
	public int insert(String statement) throws SQLException{
		return 0;
	}
	
	public static Date parse(String str, String pattern, Locale locale) {
		if(str == null || pattern == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern, locale).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Date date, String pattern, Locale locale) {
		if(date == null || pattern == null) {
			return null;
		}
		return new SimpleDateFormat(pattern, locale).format(date);
	}

	private Map<String, String> parseData(String data,String type){
		String obj=data.substring(data.indexOf("[")+1, data.lastIndexOf("]"));
		String[] array=obj.split(",");
		Map<String, String> map = new HashMap<String,String>();
		for(String str:array){
			map.put(str.trim().substring(0,str.trim().lastIndexOf("=")).trim(), str.substring(str.lastIndexOf("=")+1,str.length()));
		}
		try {
			Class<?> cls=Class.forName(type);
			Field[] ms=cls.getDeclaredFields();
			for(int i=0;i<ms.length;i++){
				if(ms[i].getType().getName().equals("java.util.Date")){
					String dateStr=map.get(ms[i].getName());
					Date date1 = parse(dateStr, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
					String str1=String.format("%tF %<tT%n", date1);
					map.put(ms[i].getName(), str1);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public int insert(String sql, Object parameter,String type) throws SQLException {
		try {
			StringBuffer finalSql=new StringBuffer(sql);
			finalSql=new StringBuffer(finalSql.substring(0, finalSql.lastIndexOf("(")+1));
			Map<String,String> paramterMap=parseData(((Object[])parameter)[0].toString(),type);
			sql=sql.substring(sql.indexOf("values"), sql.length());
			sql=sql.substring(sql.indexOf("(")+1, sql.lastIndexOf(")"));
			sql=sql.replaceAll("#", "");
			sql=sql.replace("{", "");
			sql=sql.replace("}", "");
			String[] array=sql.split(",");
			for(int i=0;i<array.length;i++){
				if(i<array.length-1){
					finalSql.append("?,");
				}else{
					finalSql.append("?)");
				}
			}
			Connection conn=ds.getConnection();
			PreparedStatement ps=conn.prepareStatement(finalSql.toString());
			for(int i=0;i<array.length;i++){
				ps.setObject(i+1, paramterMap.get(array[i]));
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("insert"+e.toString());
		}catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("insert"+e.toString());
		}
		finally{

		}
	}

	@Override
	public int update(String statement, Object parameter,String type) throws SQLException {
		try {
			Map<String,String> paramterMap=parseData(((Object[])parameter)[0].toString(),type);
			String pattern = "#\\{[a-zA-Z]*\\}";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(statement);
			List<StringBuffer> keyList=new ArrayList<StringBuffer>();
			while(m.find()){
				StringBuffer sb=new StringBuffer(m.group(0));
				sb=new StringBuffer(sb.substring(2, sb.lastIndexOf("}")));
				System.out.println(sb);
				keyList.add(sb);
			}
			
			String finalSql=statement.replaceAll(pattern, "?");
			System.out.println(finalSql);
			Connection conn=ds.getConnection();
			System.out.println(paramterMap);
			PreparedStatement ps=conn.prepareStatement(finalSql.toString());
			for(int i=0;i<keyList.size();i++){
				
				ps.setObject(i+1, paramterMap.get(keyList.get(i).toString()));
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("insert"+e.toString());
		}catch (Exception e) {
			e.printStackTrace();
			throw new SQLException("insert"+e.toString());
		}
		finally{

		}
	}
	
	public static void main(String[] args) {
		String sql="update TM_EMPLY SET NAME=#{name},FIRST_NAME=#{firstName},LAST_NAME=#{lastName},SEX=#{sex},BIRTHDAY=#{birthday} where EMP_ID=#{employeeId}";
		String pattern = "#\\{[a-zA-Z]*\\}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(sql);
		List<StringBuffer> keyList=new ArrayList<StringBuffer>();
		while(m.find()){
			System.out.println(m.group(0));
			StringBuffer sb=new StringBuffer(m.group(0));
			sb=new StringBuffer(sb.substring(2, sb.lastIndexOf("}")));
			keyList.add(sb);
		}
		
		String finalSql=sql.replaceAll(pattern, "?");
		System.out.println(finalSql);
	}

	@Override
	public int delete(String statement, Object parameter) throws SQLException {
		Object[] parameters=(Object[]) parameter;
		try {
			Connection conn=ds.getConnection();
			PreparedStatement ps=conn.prepareStatement(statement);
			for(int i=0;i<parameters.length;i++){
				ps.setObject(i+1, parameters[i]);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("selectOne()");
		}finally{

		}
	}

}
