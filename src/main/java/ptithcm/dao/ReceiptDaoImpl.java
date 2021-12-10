package ptithcm.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.Receipt;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ReceiptDaoImpl extends BaseDaoImpl<Receipt> implements ReceiptDao<Receipt> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkReceipt() {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();

		try {
			String hql = " DECLARE @ret int " + "EXEC @ret = SP_CHECK_RECEIPT "
					+ " SELECT 'Return Value' = @ret";

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

}
