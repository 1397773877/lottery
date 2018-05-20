package com.newwing.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.newwing.dao.IBaseDAO;
import com.newwing.service.IBaseService;

@SuppressWarnings("all")
public class BaseServiceImpl implements IBaseService {
	
	@Resource(name = "baseDAO")
	protected IBaseDAO baseDAO;
	
	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public List find(String hql) {
		return baseDAO.find(hql);
	}
	
	public Object get(Class c, Serializable id){
		return baseDAO.get(c, id);
	}
	public void saveOrUpdate(Object o){
		baseDAO.saveOrUpdate(o);
	}
	@Override
	public Serializable save(Object o) {
		return baseDAO.save(o);
	}
	@Override
	public void delete(Object o) {
		baseDAO.delete(o);
	}
	@Override
	public void update(Object o) {
		baseDAO.update(o);
	}
	@Override
	public Long count(String hql) {
		return baseDAO.count(hql);
	}
	@Override
	public Integer executeHql(String hql) {
		return baseDAO.executeHql(hql);
	}
	@Override
	public List find(String hql, List<Object> param) {
		return baseDAO.find(hql,param);
	}
	@Override
	public List find(String hql, Object[] param, Integer page, Integer rows) {
		return baseDAO.find(hql, param, page, rows);
	}
}
