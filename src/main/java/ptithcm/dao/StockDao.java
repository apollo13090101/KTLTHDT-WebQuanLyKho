package ptithcm.dao;

public interface StockDao<T> extends BaseDao<T> {
	public int getStockQuantity(String prod_code);
}
