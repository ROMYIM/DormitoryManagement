package com.dormitory.dao;

import java.io.Serializable;
import java.util.List;

public interface IBaseDAO<T extends Serializable, PK extends Serializable> {
	
	public Serializable save(T t);
	
	public void saveOrUpdate(T t);
	
	public void delete (T t);
	
	public void update(T t);
	
	public T queryById(Class<T> tClass, PK id);
	
	public List<T> queryAll(Class<T> tClass);
	
	public List<?> queryBySQL(String sql, Object... params);
	
	public List<?> queryBySQL2(String sql, Object[] params);
	
	public List<?> queryByHQL(String hql, Object... params);
	
	public List<?> queryByHQL2(String hql, Object[] params);
	
	public int operateByHQL(String hql, Object... params);
	
	public int operateByHQL2(String hql, Object[] params);
}
