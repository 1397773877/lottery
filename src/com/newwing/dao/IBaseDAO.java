package com.newwing.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

@SuppressWarnings("all")
public interface IBaseDAO {

	/**
	 * 保存一个对象
	 * @param o
	 * @return
	 */
	public Serializable save(Object o);

	/**
	 * 删除一个对象
	 * @param o
	 */
	public void delete(Object o);

	/**
	 * 更新一个对象
	 * @param o
	 */
	public void update(Object o);

	/**
	 * 保存或更新对象
	 * @param o
	 */
	public void saveOrUpdate(Object o);

	/**
	 * 查询
	 * @param hql
	 * @return
	 */
	public List find(String hql);

	/**
	 * 查询集合
	 * @param hql
	 * @param param
	 * @return
	 */
	public List find(String hql, Object[] param);

	/**
	 * 查询集合
	 * @param hql
	 * @param param
	 * @return
	 */
	public List find(String hql, List<Object> param);

	/**
	 * 查询集合(带分页)
	 * @param hql
	 * @param param
	 * @param page 查询第几页
	 * @param rows 每页显示几条记录
	 * @return
	 */
	public List find(String hql, Object[] param, Integer page, Integer rows);

	/**
	 * 查询集合(带分页)
	 * @param hql
	 * @param param
	 * @param page
	 * @param rows
	 * @return
	 */
	public List find(String hql, List<Object> param, Integer page, Integer rows);

	/**
	 * 获得一个对象
	 * @param c 对象类型
	 * @param id
	 * @return Object
	 */
	public Object get(Class c, Serializable id);

	/**
	 * 获得一个对象
	 * @param hql
	 * @param param
	 * @return Object
	 */
	public Object get(String hql, Object[] param);

	/**
	 * 获得一个对象
	 * @param hql
	 * @param param
	 * @return
	 */
	public Object get(String hql, List<Object> param);

	/**
	 * select count(*) from 类
	 * @param hql
	 * @return
	 */
	public Long count(String hql);

	/**
	 * select count(*) from 类
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, Object[] param);

	/**
	 * select count(*) from 类
	 * @param hql
	 * @param param
	 * @return
	 */
	public Long count(String hql, List<Object> param);

	/**
	 * 执行HQL语句
	 * @param hql
	 * @return 响应数目
	 */
	public Integer executeHql(String hql);

	/**
	 * 执行HQL语句
	 * @param hql
	 * @param param
	 * @return 响应数目
	 */
	public Integer executeHql(String hql, Object[] param);

	/**
	 * 执行HQL语句
	 * @param hql
	 * @param param
	 * @return
	 */
	public Integer executeHql(String hql, List<Object> param);
	
	/**
	 * 分页查询(修订版)
	 */
	public List findByPage(String hql, Integer startIndex, Integer pageSize);
	
	/**
	 * 保存列表
	 * @param list
	 */
	public void saveOrUpdateAll(List list);


}
