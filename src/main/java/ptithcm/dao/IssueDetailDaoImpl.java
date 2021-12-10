package ptithcm.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.IssueDetail;

@Repository
@Transactional(rollbackFor = Exception.class)
public class IssueDetailDaoImpl extends BaseDaoImpl<IssueDetail> implements IssueDetailDao<IssueDetail> {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkIssueDetail(String iss_code, String prod_code) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();

		try {
			String hql = " DECLARE @ret int " + "EXEC @ret = SP_CHECK_ISSUE_DETAIL '" + iss_code + "', '" + prod_code
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

}
