package com.lemon.session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import com.lemon.config.Configuration;

public class DataBaseOperationProxy implements InvocationHandler {

	ISqlSession sqlSession;



	public DataBaseOperationProxy(ISqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}



	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String fullClassName=method.getDeclaringClass().getName();
		String sql="";
		String methodName=method.getName();
		String returnType=method.getGenericReturnType().toString();//java.util.List<com.freud.practice.User>  
		SqlMapper mapper=Configuration.getInstance().getMapperByKey(fullClassName+"."+methodName);
		sql=mapper.getSql();
		if(mapper.getOperatorType().equals("select")){
			if (returnType.startsWith(List.class.getName()))  {
				if(args!=null && args.length>0){
					return sqlSession.selectList(sql, args);
				}else{
					return sqlSession.selectList(sql);
				}
			}else{
				if(args!=null && args.length>0){
					return sqlSession.selectOne(sql, args);
				}
			}
		}else if(mapper.getOperatorType().equals("insert")){
			return sqlSession.insert(sql, args,mapper.getParameterType());
		}else if(mapper.getOperatorType().equals("update")){
			return sqlSession.update(sql, args,mapper.getParameterType());
		}else if(mapper.getOperatorType().equals("delete")){
			return sqlSession.delete(sql, args);
		}
		return null;
	}

}
