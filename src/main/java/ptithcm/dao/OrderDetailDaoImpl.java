package ptithcm.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.OrderDetail;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderDetailDaoImpl extends BaseDaoImpl<OrderDetail> implements OrderDetailDao<OrderDetail> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkOrderDetail(String ord_code, String prod_code) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();

		try {
			String hql = " DECLARE @ret int " + "EXEC @ret = SP_CHECK_ORDER_DETAIL '" + ord_code + "', '" + prod_code
					+ "' " + "SELECT 'Return Value' = @ret";

			Query query = session.createQuery(hql);
			int result = (int) query.uniqueResult();
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e);
		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getOrderDetailQuantity(String prod_code) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		try {
			String hql = " select top 1 od.quantity from order_detail as od where od.product_code = '" + prod_code + "' order by od.created desc";
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

	@SuppressWarnings("rawtypes")
	@Override
	public BigDecimal getOrderDetailPrice(String prod_code) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		String hql = " select od.price from order_detail as od where od.product_code = '" + prod_code + "' ";
		Query query = session.createSQLQuery(hql);
		List list = query.getResultList();
		BigDecimal result = (BigDecimal) list.get(0);
		return result;
	}

}
