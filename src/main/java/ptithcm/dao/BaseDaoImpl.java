package ptithcm.dao;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Paging;

@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDaoImpl<T> implements BaseDao<T> {

	private static final Logger log = Logger.getLogger(BaseDaoImpl.class);

	@Autowired
	SessionFactory factory;

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String queryStr, Map<String, Object> mapParams, Paging paging) {
		// TODO Auto-generated method stub
		log.info("Get all records from database");

		Session session = factory.getCurrentSession();

		StringBuilder queryString = new StringBuilder("");
		StringBuilder countQuery = new StringBuilder("");

		countQuery.append(" SELECT COUNT(*) FROM ").append(getGenericName())
				.append(" AS entity WHERE entity.active = 1");
		queryString.append(" FROM ").append(getGenericName()).append(" AS entity WHERE entity.active = 1");

		if (queryStr != null && !queryStr.isEmpty()) {
			queryString.append(queryStr);
			countQuery.append(queryStr);
		}

		Query<T> query = session.createQuery(queryString.toString());
		Query<T> countQ = session.createQuery(countQuery.toString());

		if (mapParams != null && !mapParams.isEmpty()) {
			for (String key : mapParams.keySet()) {
				query.setParameter(key, mapParams.get(key));
				countQ.setParameter(key, mapParams.get(key));
			}
		}

		if (paging != null) {
			query.setFirstResult(paging.getOffset());
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (long) countQ.uniqueResult();
			paging.setTotalRows(totalRecords);
		}

		log.info("Query get all ====> " + queryString.toString());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByProperty(String property, Object value) {
		// TODO Auto-generated method stub
		log.info("Get by property");

		Session session = factory.getCurrentSession();

		StringBuilder queryString = new StringBuilder("");
		queryString.append(" FROM ").append(getGenericName()).append(" AS entity WHERE entity.active = 1 AND entity.")
				.append(property).append(" = :prop");
		Query<T> query = session.createQuery(queryString.toString());
		query.setParameter("prop", value);

		log.info("Query get by property ====> " + queryString.toString());
		return query.getResultList();
	}

	@Override
	public T getById(Class<T> entityClass, int id) {
		// TODO Auto-generated method stub
		log.info("Get by ID");
		Session session = factory.getCurrentSession();
		return session.get(entityClass, id);
	}

	@Override
	public T getByCode(Class<T> entityClass, String code) {
		// TODO Auto-generated method stub
		log.info("Get by Code");
		Session session = factory.getCurrentSession();
		return session.get(entityClass, code);
	}

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		log.info("Save instance");
		Session session = factory.getCurrentSession();
		session.persist(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		log.info("Update instance");
		Session session = factory.getCurrentSession();
		session.merge(entity);
	}

	public String getGenericName() {
		String str = getClass().getGenericSuperclass().toString();
		Pattern pattern = Pattern.compile("\\<(.*?)\\>");
		Matcher matcher = pattern.matcher(str);
		String generic = "null";
		if (matcher.find()) {
			generic = matcher.group(1);
		}
		return generic;
	}
}
