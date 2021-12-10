package ptithcm.dao;

import java.math.BigDecimal;

public interface ReceiptDetailDao<T> extends BaseDao<T> {
	public boolean checkReceiptDetail(String rec_code, String prod_code);

	public void insertReceiptDetail(String rec_code, String prod_code, int quantity, BigDecimal price);
}
