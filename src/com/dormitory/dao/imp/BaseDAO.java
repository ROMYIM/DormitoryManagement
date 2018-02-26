package com.dormitory.dao.imp;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dormitory.dao.IBaseDAO;

public class BaseDAO<T extends Serializable, PK extends Serializable> implements IBaseDAO<T, PK> {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	
	public BaseDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Serializable save(T t) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public void delete(T t) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(t);
	}

	@Override
	public void update(T t) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public T queryById(Class<T> tClass, PK id) {
		// TODO Auto-generated method stub
		return (T) sessionFactory.getCurrentSession().get(tClass, id);
	}

	@Override
	public List<T> queryAll(Class<T> tClass) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery("from" + tClass.getSimpleName());
		return query.list();
	}

	@Override
	public List<?> queryBySQL(String sql, Object... params) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	@Override
	public List<?> queryByHQL(String hql, Object... params) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	@Override
	public int operateByHQL(String hql, Object... params) {
		// TODO Auto-generated method stub
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public List<?> queryBySQL2(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return queryBySQL(sql, params);
	}

	@Override
	public List<?> queryByHQL2(String hql, Object[] params) {
		// TODO Auto-generated method stub
		return queryByHQL(hql, params);
	}

	@Override
	public int operateByHQL2(String hql, Object[] params) {
		// TODO Auto-generated method stub
		return operateByHQL(hql, params);
	}

}
