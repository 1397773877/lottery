package com.newwing.service;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

@SuppressWarnings("all")
public interface IBaseService {
	
	/**根据 ID 查询 实体类
	 * @param c
	 * @param id
	 * @return
	 */
	public Object get(Class c, Serializable id);
	/**根据HQL语句查询
	 * @param hql
	 * @return
	 */
	public List find(String hql);
	/**
	 * 保存或更新对象
	 * 
	 * @param o
	 */
	public void saveOrUpdate(Object o);
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	public Serializable save(Object o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 */
	public void delete(Object o);

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 */
	public void update(Object o);
	/**
	 * select count(*) from 类
	 * 
	 * @param hql
	 * @return
	 */
	public Long count(String hql);
	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer executeHql(String hql);

	/**
	 * 查询集合
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public List find(String hql, List<Object> param);

	/**
	 * 查询集合(带分页)
	 * 
	 * @param hql
	 * @param param
	 * @param page
	 *            查询第几页
	 * @param rows
	 *            每页显示几条记录
	 * @return
	 */
	public List find(String hql, Object[] param, Integer page, Integer rows);
}
