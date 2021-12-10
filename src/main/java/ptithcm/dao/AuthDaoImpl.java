package ptithcm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ptithcm.entity.Auth;

@Repository
@Transactional(rollbackFor = Exception.class)
public class AuthDaoImpl extends BaseDaoImpl<Auth> implements AuthDao<Auth> {

	@SuppressWarnings("unchecked")
	@Override
	public Auth find(int roleId, int menuId) {
		// TODO Auto-generated method stub
		Session session = factory.getCurrentSession();
		
		String hql = " FROM Auth AS entity WHERE entity.role.id = :roleId AND entity.menu.id = :menuId";
		Query<Auth> query = session.createQuery(hql);
		query.setParameter("roleId", roleId);
		query.setParameter("menuId", menuId);
		
		List<Auth> auths = query.getResultList();
		if (!CollectionUtils.isEmpty(auths)) {
			return auths.get(0);
		}
		
		return null;
	}

}
