package ptithcm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ptithcm.entity.User;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao<User> {

	@SuppressWarnings("rawtypes")
	@Override
	public int getNumberOfUsers() {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		try {
			String hql = " select count(u.id) from users as u where u.active = 1 ";
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
