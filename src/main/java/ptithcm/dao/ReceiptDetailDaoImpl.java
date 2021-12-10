package ptithcm.dao;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.ReceiptDetail;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ReceiptDetailDaoImpl extends BaseDaoImpl<ReceiptDetail> implements ReceiptDetailDao<ReceiptDetail> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkReceiptDetail(String rec_code, String prod_code) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();

		try {
			String hql = " DECLARE @ret int " + "EXEC @ret = SP_CHECK_RECEIPT_DETAIL '" + rec_code + "', '" + prod_code
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

	@Override
	public void insertReceiptDetail(String rec_code, String prod_code, int quantity, BigDecimal price) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();

		try {
			String hql = " EXEC SP_INSERT_RECEIPT_DETAIL '" + rec_code + "', '" + prod_code + "', " + quantity + ", "
					+ price;

			session.createQuery(hql);

			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			transaction.rollback();
			e.printStackTrace();
			System.out.println(e);
		} finally {
			session.close();
		}
	}

}
