package net.cpszju.localization.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseDao<T> {
	private Class<T> entityClass;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	public BaseDao(){
		ParameterizedType ptype = (ParameterizedType)this.getClass().getGenericSuperclass();
		Type[] paramtype = ptype.getActualTypeArguments();
		entityClass = (Class)paramtype[0];
	}
	
	public T get(Serializable id){
		return hibernateTemplate.get(entityClass, id);
	}
	
	public T load(Serializable id){
		return hibernateTemplate.load(entityClass, id);
	}
	public void save(T entity){
		hibernateTemplate.save(entity);
	}
	
	public void delete(T entity){
		hibernateTemplate.delete(entity);
	}
	
	public void update(T entity){
		hibernateTemplate.update(entity);
	}
	
	public List<T> find(String hql){
		return hibernateTemplate.find(hql);
	}
	
	public HibernateTemplate getHibernateTemplate(){
		return hibernateTemplate;
	}
}
