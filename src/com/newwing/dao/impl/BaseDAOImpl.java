package com.newwing.dao.impl;

import java.io.Serializable;
import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newwing.dao.IBaseDAO;

/**
 * 基础 DAOImpl
 * @author Teddy
 */
@Repository("baseDAO")
@SuppressWarnings("all")
public class BaseDAOImpl implements IBaseDAO {
	
/*	private Class clazz;//实体类型
	public BaseDAOImpl(){//注入
		//给clazz赋值，让他知道子类到底操作的那个实体类型
		Type type = this.getClass().getGenericSuperclass();//得到当前类型的带有泛型类型的父类型   BaseDAOImpl<Customer>
		ParameterizedType pType = (ParameterizedType)type;//BaseDao<Customer>的类型就是ParameterizedType；如同class的类型Class一样的。
		clazz = (Class)pType.getActualTypeArguments()[0];// <Customer>
	}*/
	
	@Autowired
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Serializable save(Object o) {
		return this.getCurrentSession().save(o);
	}

	public void delete(Object o) {
		this.getCurrentSession().delete(o);
	}

	public void update(Object o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(Object o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public List find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	public List find(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i+"", param[i]);
			}
		}
		return q.list();
	}

	public List find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i+"", param.get(i));
			}
		}
		return q.list();
	}

	public List find(String hql, Object[] param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i+"", param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	/**
	 * 分页查询(修订版)
	 */
	public List findByPage(String hql, Integer startIndex, Integer pageSize) {
		
		if (null == startIndex || startIndex < 0) {
			startIndex = 0;
		}
		if (null == pageSize || pageSize < 1) {
			pageSize = 10; //默认显示10条
		}
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult(startIndex).setMaxResults(pageSize).list();
		
	}

	public List find(String hql, List<Object> param, Integer page,
			Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i+"", param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public Object get(Class c, Serializable id) {
		return (Object) this.getCurrentSession().get(c, id);
	}

	public Object get(String hql, Object[] param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public Object get(String hql, List<Object> param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public Long count(String hql) {
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public Long count(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i+"", param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i+"", param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i+"", param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i+"", param.get(i));
			}
		}
		return q.executeUpdate();
	}
	
	public void saveOrUpdateAll(List list) {
		for (Object object: list) {
			this.saveOrUpdate(object);
		}
	}

}
