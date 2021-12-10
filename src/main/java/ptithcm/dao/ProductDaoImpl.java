package ptithcm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Product;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao<Product> {

	@SuppressWarnings("rawtypes")
	@Override
	public int getNumberOfProducts() {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		try {
			String hql = " select count(p.code) from product as p where p.active = 1 ";
			Query query = session.createSQLQuery(hql);
			List list = query.getResultList();
			int result = (int) list.get(0);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e);
		}
		return 0;
	}

}
