package ptithcm.dao;

import java.util.List;
import java.util.Map;

import ptithcm.entity.Paging;

public interface BaseDao<T> {
	public List<T> getAll(String queryStr, Map<String, Object> mapParams, Paging paging);

	public List<T> getByProperty(String property, Object value);

	public T getById(Class<T> entityClass, int id);

	public T getByCode(Class<T> entityClass, String code);

	public void save(T entity);

	public void update(T entity);
}
