package ptithcm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Stock;

@Repository
@Transactional(rollbackFor = Exception.class)
public class StockDaoImpl extends BaseDaoImpl<Stock> implements StockDao<Stock> {

	@SuppressWarnings("rawtypes")
	@Override
	public int getStockQuantity(String prod_code) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		String hql = " select s.quantity from stock as s where s.product_code = '" + prod_code + "' ";
		Query query = session.createSQLQuery(hql);
		List list = query.getResultList();
		int result = (int) list.get(0);
		return result;
	}
}
