package ptithcm.dao;

public interface UserDao<T> extends BaseDao<T> {
	public int getNumberOfUsers();
}
