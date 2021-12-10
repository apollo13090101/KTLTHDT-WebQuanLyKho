package ptithcm.dao;

import ptithcm.entity.UserRole;

public interface UserRoleDao<T> extends BaseDao<T> {
	public UserRole find(int roleId, int userId);
}
