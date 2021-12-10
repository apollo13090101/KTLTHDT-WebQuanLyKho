package ptithcm.dao;

import ptithcm.entity.Auth;

public interface AuthDao<T> extends BaseDao<T> {
	public Auth find(int roleId, int menuId);
}
