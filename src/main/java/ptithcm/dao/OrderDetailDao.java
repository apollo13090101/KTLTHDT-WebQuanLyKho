package ptithcm.dao;

import java.math.BigDecimal;

public interface OrderDetailDao<T> extends BaseDao<T> {
	public boolean checkOrderDetail(String ord_code, String prod_code);

	public int getOrderDetailQuantity(String prod_code);

	public BigDecimal getOrderDetailPrice(String prod_code);
}
