package ptithcm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ptithcm.entity.UserRole;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao<UserRole> {

	@SuppressWarnings("unchecked")
	@Override
	public UserRole find(int roleId, int userId) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();

		String hql = " FROM UserRole AS entity WHERE entity.role.id = :roleId AND entity.user.id = :userId";
		Query<UserRole> query = session.createQuery(hql);
		query.setParameter("roleId", roleId);
		query.setParameter("userId", userId);

		List<UserRole> userRoles = query.getResultList();
		if (!CollectionUtils.isEmpty(userRoles)) {
			return userRoles.get(0);
		}

		return null;
	}

}
