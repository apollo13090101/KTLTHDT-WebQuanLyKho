package ptithcm.dao;

public interface ProductDao<T> extends BaseDao<T> {
	public int getNumberOfProducts();
}
